package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class McqAnswerRequest {
    private Long questionId;
    private String selectedOption;
}