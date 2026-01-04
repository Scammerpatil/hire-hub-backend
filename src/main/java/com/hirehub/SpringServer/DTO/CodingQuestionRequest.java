package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class CodingQuestionRequest {
    private Long testId;
    private String title;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String difficulty;
}
