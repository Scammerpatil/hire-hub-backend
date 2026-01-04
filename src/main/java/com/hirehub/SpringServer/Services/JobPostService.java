package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.JobPost;

import java.util.List;
import java.util.Optional;

public interface JobPostService {
    JobPost createJob(JobPost job);
    JobPost updateJob(Long jobId, JobPost job);
    void deleteJob(Long jobId);
    Optional<JobPost> getJobById(Long jobId);
    List<JobPost> getJobsByCompany(Long companyId);
    List<JobPost> getAllJobs();
    List<JobPost> getJobsByCompanyUserId(Long userId);
    List<JobPost> getJobsByStatus(String status);
}

