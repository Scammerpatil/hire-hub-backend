package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.Company;
import com.hirehub.SpringServer.Entity.JobPost;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobPostResponse {
    private Long jobId;
    private Company company;
    private String jobTitle;
    private String jobPosition;
    private String jobLocation;
    private String jobCategory;
    private String jobDescription;
    private String requiredDegrees;
    private Integer requiredExperience;
    private String requiredSkills;;
    private Double maxPackage;
    private Double minPackage;
    private Integer totalOpenings;
    private Boolean testRequired;
    private String status;
    private Timestamp postedAt;

    public JobPostResponse(JobPost job) {
        this.jobId = job.getJobId();
        this.company = job.getCompany();
        this.jobTitle = job.getJobTitle();
        this.jobPosition = job.getJobPosition();
        this.jobLocation = job.getJobLocation();
        this.jobCategory = job.getJobCategory();
        this.jobDescription = job.getJobDescription();
        this.requiredDegrees = job.getRequiredDegrees();
        this.requiredExperience = job.getRequiredExperience();
        this.requiredSkills = job.getRequiredSkills();
        this.jobCategory = job.getJobCategory();
        this.maxPackage = job.getMaxPackage();
        this.minPackage = job.getMinPackage();
        this.totalOpenings = job.getTotalOpenings();
        this.status = job.getStatus();
        this.postedAt = job.getPostedAt();
    }
}
