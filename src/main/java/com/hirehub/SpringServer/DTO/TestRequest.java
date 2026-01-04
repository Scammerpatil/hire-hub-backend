package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class TestRequest {
    private Long jobId;
    private String testType;
    private Integer durationMinutes;
}
