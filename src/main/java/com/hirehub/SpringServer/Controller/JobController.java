package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.JobPostResponse;
import com.hirehub.SpringServer.Entity.Company;
import com.hirehub.SpringServer.Entity.JobPost;
import com.hirehub.SpringServer.Services.CompanyService;
import com.hirehub.SpringServer.Services.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class JobController {

    private final JobPostService jobPostService;
    private final CompanyService companyService;

    @GetMapping("/company/{userId}")
    public ResponseEntity<List<JobPostResponse>> getJobsByCompany(@PathVariable Long userId)    {
        Optional<Company> company = companyService.getCompanyByUserId(userId);
        if(company.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<JobPost> jobs = jobPostService.getJobsByCompany(company.get().getCompanyId());

        List<JobPostResponse> responses = jobs.stream()
                .map(JobPostResponse::new)
                .toList();


        return ResponseEntity.ok(responses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobPostResponse>> getAllJobs() {

        List<JobPost> jobs = jobPostService.getAllJobs();

        List<JobPostResponse> responses = jobs.stream()
                .map(JobPostResponse::new)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-job-details/{jobId}")
    public ResponseEntity<Optional<JobPost>> getJob(@PathVariable Long jobId){
        Optional<JobPost> job = jobPostService.getJobById(jobId);
        return ResponseEntity.ok(job);
    }
}

