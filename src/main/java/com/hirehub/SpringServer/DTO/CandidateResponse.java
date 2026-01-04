package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CandidateResponse {
    private Long candidateId;
    private UserDTO user;
    private String degree;
    private Integer experienceYears;
    private String skills;
    private String resumeUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
