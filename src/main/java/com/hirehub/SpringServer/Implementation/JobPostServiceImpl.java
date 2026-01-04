package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.Entity.JobPost;
import com.hirehub.SpringServer.Repository.JobPostRepository;
import com.hirehub.SpringServer.Services.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    @Override
    public JobPost createJob(JobPost job) {
        return jobPostRepository.save(job);
    }

    @Override
    public JobPost updateJob(Long jobId, JobPost updatedJob) {
        JobPost existingJob = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingJob.setJobTitle(updatedJob.getJobTitle());
        existingJob.setJobPosition(updatedJob.getJobPosition());
        existingJob.setJobLocation(updatedJob.getJobLocation());
        existingJob.setJobCategory(updatedJob.getJobCategory());
        existingJob.setJobDescription(updatedJob.getJobDescription());
        existingJob.setRequiredDegrees(updatedJob.getRequiredDegrees());
        existingJob.setRequiredExperience(updatedJob.getRequiredExperience());
        existingJob.setRequiredSkills(updatedJob.getRequiredSkills());
        existingJob.setMinPackage(updatedJob.getMinPackage());
        existingJob.setMaxPackage(updatedJob.getMaxPackage());
        existingJob.setTotalOpenings(updatedJob.getTotalOpenings());
        existingJob.setStatus(updatedJob.getStatus());

        return jobPostRepository.save(existingJob);
    }

    @Override
    public void deleteJob(Long jobId) {
        jobPostRepository.deleteById(jobId);
    }

    @Override
    public Optional<JobPost> getJobById(Long jobId) {
        return jobPostRepository.findById(jobId);
    }

    @Override
    public List<JobPost> getJobsByCompany(Long companyId) {
        return jobPostRepository.findByCompany_CompanyId(companyId);
    }

    @Override
    public List<JobPost> getAllJobs() {
        return jobPostRepository.findAll();
    }

    @Override
    public List<JobPost> getJobsByStatus(String status) {
        return jobPostRepository.findByStatus(status);
    }

    @Override
    public List<JobPost> getJobsByCompanyUserId(Long userId) {
        return jobPostRepository.findByCompanyUserUserId(userId);
    }
}
