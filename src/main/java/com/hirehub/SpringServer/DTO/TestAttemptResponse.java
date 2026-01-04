package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestAttemptResponse {
    private Long attemptId;
    private Integer score;
    private Timestamp startedAt;
    private Timestamp submittedAt;
}