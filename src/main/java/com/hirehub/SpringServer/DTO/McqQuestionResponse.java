package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class McqQuestionResponse {
    private Long questionId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public McqQuestionResponse(Long questionId, String questionText, String optionA, String optionB, String optionC, String optionD) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }
}