package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CodingSubmissionResponse {
    private Long submissionId;
    private String verdict;
    private Timestamp submittedAt;
}
