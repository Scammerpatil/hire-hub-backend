package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.util.List;

@Data
public class McqSubmitRequest {
    private Long candidateId;
    private Long jobId;
    private List<McqAnswerRequest> answers;
}