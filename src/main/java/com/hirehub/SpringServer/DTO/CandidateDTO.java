package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class CandidateDTO {

    private Long candidateId;

    private Long userId;
    private String fullName;
    private String email;
    private String profileImage;

    private String degree;
    private Integer experienceYears;

    private String resumeUrl;

    // Useful summary fields
    private String currentJobTitle;
    private String currentCompany;
}
