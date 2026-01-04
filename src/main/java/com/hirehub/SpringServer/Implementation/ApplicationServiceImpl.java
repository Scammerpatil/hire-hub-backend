package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.DTO.McqAnswerRequest;
import com.hirehub.SpringServer.DTO.McqQuestionResponse;
import com.hirehub.SpringServer.Entity.Application;
import com.hirehub.SpringServer.Entity.Candidate;
import com.hirehub.SpringServer.Entity.JobPost;
import com.hirehub.SpringServer.Entity.McqQuestion;
import com.hirehub.SpringServer.Repository.ApplicationRepository;
import com.hirehub.SpringServer.Repository.McqQuestionRepository;
import com.hirehub.SpringServer.Services.ApplicationService;
import com.hirehub.SpringServer.Services.CandidateService;
import com.hirehub.SpringServer.Services.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CandidateService candidateService;
    private final JobPostService jobPostService;
    private final McqQuestionRepository mcqQuestionRepository;

    @Override
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Optional<Application> getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }

    @Override
    public List<Application> getApplicationsByCandidate(Long candidateId) {
        return applicationRepository.findByCandidate_CandidateId(candidateId);
    }

    @Override
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJob_JobId(jobId);
    }

    @Override
    public boolean hasAlreadyApplied(Long candidateId, Long jobId) {
        return applicationRepository
                .existsByCandidateCandidateIdAndJobJobId(candidateId, jobId);
    }

    @Override
    public boolean validateMcqs(List<McqAnswerRequest> answers) {
        System.out.println("Validating MCQ answers: " + answers);

        for (McqAnswerRequest ans : answers) {
            McqQuestion q = mcqQuestionRepository.findById(ans.getQuestionId())
                    .orElseThrow(() ->
                            new RuntimeException("Question not found: " + ans.getQuestionId())
                    );
            String correct = q.getCorrectOption() == null ? null : q.getCorrectOption().toString().trim();
            String selected = ans.getSelectedOption() == null ? null : ans.getSelectedOption().toString().trim();
            if (!java.util.Objects.equals(correct, selected)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String isEligible(Long candidateId, Long jobId) {
        var candidateOpt = candidateService.getCandidateByUserId(candidateId);
        if (candidateOpt.isEmpty()) return "Candidate not found";
        Candidate candidate = candidateOpt.get();

        var jobOpt = jobPostService.getJobById(jobId);
        if (jobOpt.isEmpty()) return "Job not found";
        JobPost job = jobOpt.get();

        List<String> errors = new ArrayList<>();

        if (job.getRequiredExperience() != null) {
            if (candidate.getExperienceYears() == null ||
                    candidate.getExperienceYears() < job.getRequiredExperience()) {
                errors.add("Required experience: " + job.getRequiredExperience() +
                        " years, but candidate has " +
                        (candidate.getExperienceYears() == null ? "none" : candidate.getExperienceYears()));
            }
        }

        if (job.getRequiredDegrees() != null && !job.getRequiredDegrees().isBlank()) {

            String jobDegrees = job.getRequiredDegrees().toLowerCase();
            String candidateDegree = candidate.getDegree() != null
                    ? candidate.getDegree().toLowerCase()
                    : "";

            boolean degreeMatch = false;
            for (String reqDegree : jobDegrees.split(",")) {
                if (candidateDegree.contains(reqDegree.trim())) {
                    degreeMatch = true;
                    break;
                }
            }
            if (!degreeMatch) {
                errors.add("Candidate degree (" + candidate.getDegree() +
                        ") does not match required degrees (" + job.getRequiredDegrees() + ")");
            }
        }

        if (job.getRequiredSkills() != null && !job.getRequiredSkills().isBlank()) {

            if (candidate.getSkills() == null || candidate.getSkills().isBlank()) {
                errors.add("Candidate has no skills listed but job requires: " + job.getRequiredSkills());
            } else {
                String[] jobSkills = job.getRequiredSkills().toLowerCase().split(",");
                String candidateSkills = candidate.getSkills().toLowerCase();

                List<String> missingSkills = new ArrayList<>();

                for (String skill : jobSkills) {
                    if (!candidateSkills.contains(skill.trim())) {
                        missingSkills.add(skill.trim());
                    }
                }

                if (!missingSkills.isEmpty()) {
                    errors.add("Missing required skills: " + String.join(", ", missingSkills));
                }
            }
        }

        if (errors.isEmpty()) {
            return "Eligible";
        }

        return String.join("; ", errors);
    }

    @Override
    public List<McqQuestionResponse> getMcqsForScreening() {
        return mcqQuestionRepository.findRandomMcqs()
                .stream()
                .map(mcq -> new McqQuestionResponse(
                        mcq.getQuestionId(),
                        mcq.getQuestionText(),
                        mcq.getOptionA(),
                        mcq.getOptionB(),
                        mcq.getOptionC(),
                        mcq.getOptionD()
                ))
                .toList();
    }
}
