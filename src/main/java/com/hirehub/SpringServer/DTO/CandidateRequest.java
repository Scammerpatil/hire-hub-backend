package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.User;
import lombok.Data;

@Data
public class CandidateRequest {
    private Long candidateId;
    private User user;
    private String degree;
    private Integer experienceYears;
    private String skills;
    private String resumeUrl;
    private String education;
    private String experience;
    private String certifications;
    private String projects;
    private String currentJobTitle;
    private String currentCompany;
    private Integer currentSalary;
    private Integer expectedSalary;
    private String noticePeriod;
    private String resumeHeadline;
    private String summary;
    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;
    private String preferredLocations;
    private String employmentType;
    private String workType;
}
