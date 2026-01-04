package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class CodingSubmissionRequest {
    private Long attemptId;
    private Long codeQuestionId;
    private String code;
    private String language;
}

