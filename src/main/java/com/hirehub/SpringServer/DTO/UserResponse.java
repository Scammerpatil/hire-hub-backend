package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private Timestamp createdAt;
}
