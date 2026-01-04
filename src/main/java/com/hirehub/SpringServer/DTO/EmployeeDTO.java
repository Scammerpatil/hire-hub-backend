package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long employeeId;

    private Long userId;      // reference to user
    private String userName;  // from User.fullName
    private String email;     // from User.email

    private String profileImage;

    private Long companyId;
    private String companyName;
}
