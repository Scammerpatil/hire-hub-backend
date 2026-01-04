package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String fullName;
    private String email;
    private String role;
}
