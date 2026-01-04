package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ApplicationResponse {
    private Long applicationId;
    private CandidateDTO candidate;
    private JobPostDTO job;
    private Long referredByEmployeeId;
    private String referredByEmployeeName;
    private String status;
    private Timestamp createdAt;
}