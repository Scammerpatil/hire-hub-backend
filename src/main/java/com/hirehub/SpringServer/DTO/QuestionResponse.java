package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class QuestionResponse {
    private Long questionId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Character correctOption;
}
