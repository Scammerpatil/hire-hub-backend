package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.util.List;

@Data
public class JobApplicationRequest {
    private Long candidateId;
    private Long jobId;
}