package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class CodingQuestionResponse {
    private Long codeQuestionId;
    private String title;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String difficulty;
}