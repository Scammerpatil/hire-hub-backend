package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.*;
import com.hirehub.SpringServer.Entity.*;
import com.hirehub.SpringServer.Repository.ApplicationRepository;
import com.hirehub.SpringServer.Repository.JobPostRepository;
import com.hirehub.SpringServer.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/application")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CandidateService candidateService;
    private final JobPostService jobPostService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusLogService statusLogService;
    private final EmployeeService employeeService;
    private final ReferralService referralService;

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationWithStatusLog> getApplicationById(
            @PathVariable Long applicationId
    ) {
        Optional<Application> applicationOpt =
                applicationService.getApplicationById(applicationId);

        if (applicationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Application application = applicationOpt.get();

        // ---------------------------
        // STATUS LOGS
        // ---------------------------
        List<ApplicationStatusLogResponse> statusLogs =
                statusLogService.getLogsByApplication(applicationId)
                        .stream()
                        .map(l -> {
                            ApplicationStatusLogResponse r =
                                    new ApplicationStatusLogResponse();
                            r.setLogId(l.getLogId());
                            r.setFromStatus(l.getFromStatus());
                            r.setToStatus(l.getToStatus());
                            r.setUpdatedAt(l.getUpdatedAt());
                            return r;
                        })
                        .toList();

        // ---------------------------
        // JOB DTO
        // ---------------------------
        JobPost job = application.getJob();

        JobPostDTO jobPostDTO = new JobPostDTO();
        jobPostDTO.setJobId(job.getJobId());
        jobPostDTO.setJobTitle(job.getJobTitle());
        jobPostDTO.setJobType(job.getJobCategory());
        jobPostDTO.setRequiredDegrees(job.getRequiredDegrees());
        jobPostDTO.setRequiredExperience(job.getRequiredExperience());
        jobPostDTO.setMinPackage(job.getMinPackage());
        jobPostDTO.setMaxPackage(job.getMaxPackage());
        jobPostDTO.setStatus(job.getStatus());
        jobPostDTO.setCompanyId(job.getCompany().getCompanyId());
        jobPostDTO.setCompanyName(job.getCompany().getUser().getFullName());

        // ---------------------------
        // FINAL RESPONSE
        // ---------------------------
        ApplicationWithStatusLog response = new ApplicationWithStatusLog();
        response.setApplicationId(application.getApplicationId());
        response.setStatus(application.getStatus());
        response.setAppliedAt(application.getCreatedAt());
        response.setJobPostDTO(jobPostDTO);
        response.setCompanyId(job.getCompany().getCompanyId());
        response.setCompanyName(job.getCompany().getUser().getFullName());
        response.setStatusLogs(statusLogs);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/apply/submit")
    public ResponseEntity<?> submitMcqAndApply(
            @RequestBody McqSubmitRequest request
    ) {

        if (applicationService.hasAlreadyApplied(
                request.getCandidateId(), request.getJobId())) {
            return ResponseEntity.badRequest()
                    .body("You have already applied for this job.");
        }

        String eligibility = applicationService
                .isEligible(request.getCandidateId(), request.getJobId());

        if (!"Eligible".equals(eligibility)) {
            return ResponseEntity.badRequest().body(eligibility);
        }   

        Candidate candidate = candidateService
                .getCandidateByUserId(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        JobPost job = jobPostService
                .getJobById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus("applied");
        application.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        applicationService.createApplication(application);

        return ResponseEntity.ok("Application submitted successfully");
    }

    @GetMapping("/company/{userId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForCompanyJobs(
            @PathVariable Long userId
    ) {

        // 1️⃣ Get all jobs for this company user
        List<JobPost> jobs = jobPostService.getJobsByCompanyUserId(userId);

        if (jobs.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        // 2️⃣ Collect all applications for these jobs
        List<Application> applications = jobs.stream()
                .flatMap(job ->
                        applicationService.getApplicationsByJob(job.getJobId()).stream()
                )
                .toList();

        // 3️⃣ Map to DTO
        List<ApplicationResponse> response = applications.stream()
                .map(app -> {

                    Candidate candidate = app.getCandidate();
                    JobPost job = app.getJob();

                    // -------- Candidate DTO --------
                    CandidateDTO candidateDTO = new CandidateDTO();
                    candidateDTO.setCandidateId(candidate.getCandidateId());
                    candidateDTO.setUserId(candidate.getUser().getUserId());
                    candidateDTO.setFullName(candidate.getUser().getFullName());
                    candidateDTO.setProfileImage(candidate.getUser().getProfileImage());
                    candidateDTO.setEmail(candidate.getUser().getEmail());
                    candidateDTO.setDegree(candidate.getDegree());
                    candidateDTO.setExperienceYears(candidate.getExperienceYears());
                    candidateDTO.setResumeUrl(candidate.getResumeUrl());
                    candidateDTO.setCurrentJobTitle(candidate.getCurrentJobTitle());
                    candidateDTO.setCurrentCompany(candidate.getCurrentCompany());

                    // -------- Job DTO --------
                    JobPostDTO jobDTO = new JobPostDTO();
                    jobDTO.setJobId(job.getJobId());
                    jobDTO.setJobTitle(job.getJobTitle());
                    jobDTO.setJobType(job.getJobCategory());
                    jobDTO.setRequiredDegrees(job.getRequiredDegrees());
                    jobDTO.setRequiredExperience(job.getRequiredExperience());
                    jobDTO.setMinPackage(job.getMinPackage());
                    jobDTO.setMaxPackage(job.getMaxPackage());
                    jobDTO.setStatus(job.getStatus());
                    jobDTO.setCompanyId(job.getCompany().getCompanyId());
                    jobDTO.setCompanyName(job.getCompany().getUser().getFullName());

                    // -------- Application Response --------
                    ApplicationResponse ar = new ApplicationResponse();
                    ar.setApplicationId(app.getApplicationId());
                    ar.setStatus(app.getStatus());
                    ar.setCreatedAt(app.getCreatedAt());
                    ar.setCandidate(candidateDTO);
                    ar.setJob(jobDTO);

                    // -------- Referral Lookup (OPTIONAL) --------
                    referralService
                            .getReferralByCandidateAndJob(
                                    candidate.getCandidateId(),
                                    job.getJobId()
                            )
                            .ifPresent(ref -> {
                                ar.setReferredByEmployeeId(
                                        ref.getEmployee().getEmployeeId()
                                );
                                ar.setReferredByEmployeeName(
                                        ref.getEmployee().getUser().getFullName()
                                );
                            });

                    return ar;
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{userId}")
    public ResponseEntity<List<Application>> getApplicationsForEmployeeCompanyJobs(
            @PathVariable Long userId
    ) {
        Employee employee = employeeService
                .getEmployeeByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Long companyId = employee.getCompany().getUser().getUserId();

        List<JobPost> jobs = jobPostService.getJobsByCompanyUserId(companyId);
        List<Application> allApplications = jobs.stream()
                .flatMap(job -> applicationService.getApplicationsByJob(job.getJobId()).stream())
                .toList();
        return ResponseEntity.ok(allApplications);
    }

    @GetMapping("/candidate/{userId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForCandidate(
            @PathVariable Long userId
    ) {
        Candidate candidate = candidateService
                .getCandidateByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        System.out.println(candidate);
        List<Application> applications =
                applicationService.getApplicationsByCandidate(candidate.getCandidateId());

        List<ApplicationResponse> response = applications.stream()
                .map(app -> {

                    // -------- Candidate DTO --------
                    CandidateDTO candidateDTO = new CandidateDTO();
                    candidateDTO.setCandidateId(candidate.getCandidateId());
                    candidateDTO.setUserId(candidate.getUser().getUserId());
                    candidateDTO.setProfileImage(candidate.getUser().getProfileImage());
                    candidateDTO.setFullName(candidate.getUser().getFullName());
                    candidateDTO.setEmail(candidate.getUser().getEmail());
                    candidateDTO.setDegree(candidate.getDegree());
                    candidateDTO.setExperienceYears(candidate.getExperienceYears());
                    candidateDTO.setResumeUrl(candidate.getResumeUrl());
                    candidateDTO.setCurrentJobTitle(candidate.getCurrentJobTitle());
                    candidateDTO.setCurrentCompany(candidate.getCurrentCompany());

                    // -------- Job DTO --------
                    JobPost job = app.getJob();
                    JobPostDTO jobDTO = new JobPostDTO();
                    jobDTO.setJobId(job.getJobId());
                    jobDTO.setJobTitle(job.getJobTitle());
                    jobDTO.setJobType(job.getJobCategory());
                    jobDTO.setRequiredDegrees(job.getRequiredDegrees());
                    jobDTO.setRequiredExperience(job.getRequiredExperience());
                    jobDTO.setMinPackage(job.getMinPackage());
                    jobDTO.setMaxPackage(job.getMaxPackage());
                    jobDTO.setStatus(job.getStatus());
                    jobDTO.setCompanyId(job.getCompany().getCompanyId());
                    jobDTO.setCompanyName(job.getCompany().getUser().getFullName());

                    // -------- Application Response --------
                    ApplicationResponse ar = new ApplicationResponse();
                    ar.setApplicationId(app.getApplicationId());
                    ar.setStatus(app.getStatus());
                    ar.setCreatedAt(app.getCreatedAt());
                    ar.setCandidate(candidateDTO);
                    ar.setJob(jobDTO);

                    // -------- Referral Lookup (OPTIONAL) --------
                    referralService
                            .getReferralByCandidateAndJob(
                                    candidate.getCandidateId(),
                                    job.getJobId()
                            )
                            .ifPresent(ref -> {
                                ar.setReferredByEmployeeId(
                                        ref.getEmployee().getEmployeeId()
                                );
                                ar.setReferredByEmployeeName(
                                        ref.getEmployee().getUser().getFullName()
                                );
                            });

                    return ar;
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/withdraw/{applicationId}")
    public ResponseEntity<String> withdraw(@PathVariable Long applicationId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!"applied".equalsIgnoreCase(application.getStatus())) {
            return ResponseEntity.badRequest()
                    .body("Cannot withdraw application at this stage");
        }

        application.setStatus("withdrawn");
        applicationRepository.save(application);

        return ResponseEntity.ok("Application withdrawn successfully");
    }

    @PostMapping("/employee/refer")
    public ResponseEntity<String> refer(
            @RequestBody ReferralRequest referral
    ) {
        Employee employee = employeeService
                .getEmployeeByUserId(referral.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Candidate candidate = candidateService
                .getCandidateById(referral.getReferredCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        JobPost job = jobPostService
                .getJobById(referral.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!employee.getCompany().getCompanyId()
                .equals(job.getCompany().getCompanyId())) {
            return ResponseEntity
                    .badRequest()
                    .body("Employee does not belong to this company");
        }

        Referral refer = new Referral();
        refer.setEmployee(employee);
        refer.setReferredCandidate(candidate);
        refer.setJob(job);
        refer.setReferralDate(new Timestamp(System.currentTimeMillis()));

        referralService.createReferral(refer);

        return ResponseEntity.ok("Candidate referred successfully");
    }

    @GetMapping("/employee/refer/{userId}")
    public ResponseEntity<List<Referral>> referredCandidates(
            @PathVariable Long userId
    ) {
        Employee employee = employeeService
                .getEmployeeByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Referral> referrals =
                referralService.getReferralsByEmployee(employee.getEmployeeId());

        return ResponseEntity.ok(referrals);
    }
}
