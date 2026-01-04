package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ApplicationStatusLogResponse {
    private Long logId;
    private String fromStatus;
    private String toStatus;
    private Timestamp updatedAt;
}
