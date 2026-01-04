package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class JobPostDTO {

    private Long jobId;
    private String jobTitle;

    private String jobType;

    private String requiredDegrees;
    private Integer requiredExperience;

    private Double minPackage;
    private Double maxPackage;

    private String status;

    private Long companyId;
    private String companyName;
}
