package com.hirehub.SpringServer.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CompanyResponse {
    private Long companyId;
    private UserDTO user;
    private String companyName;
    private String address;
    private String contactEmail;
    private String contactPhone;
    private String industry;
    private String companySize;
    private String website;
    private Boolean isBlocked;
    private String description;
    private Timestamp createdAt;
}
