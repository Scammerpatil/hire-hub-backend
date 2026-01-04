package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class ReferralRequest {
    private Long employeeId;
    private Long referredCandidateId;
    private Long jobId;
}
