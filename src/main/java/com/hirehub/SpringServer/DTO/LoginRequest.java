package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String role;
    private String password;
}
