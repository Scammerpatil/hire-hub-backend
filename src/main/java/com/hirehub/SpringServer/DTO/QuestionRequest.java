package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class QuestionRequest {
    private Long testId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Character correctOption;
}
