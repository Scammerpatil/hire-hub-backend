package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String phone;
    private String profileImage;
    private String role;
    private String password;
}
