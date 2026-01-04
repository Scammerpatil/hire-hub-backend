package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.JobPost;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestResponse {
    private Long testId;
    private JobPost job;
    private String testType;
    private Integer durationMinutes;
    private Timestamp createdAt;
}
