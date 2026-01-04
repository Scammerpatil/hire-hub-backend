package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReferralResponse {
    private Long referralId;
    private EmployeeDTO employee;
    private CandidateDTO referredCandidate;
    private JobPostDTO job;
    private Timestamp referralDate;
}

