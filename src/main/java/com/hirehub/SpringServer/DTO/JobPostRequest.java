package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class JobPostRequest {
    private Long companyId;

    private String jobTitle;
    private String jobPosition;
    private String jobLocation;
    private String jobCategory;

    private String jobDescription;

    private String requiredDegrees;
    private Integer requiredExperience;
    private String requiredSkills;

    private Double minPackage;
    private Double maxPackage;
    private Integer totalOpenings;

    private Boolean testRequired;
    private String status;
}
