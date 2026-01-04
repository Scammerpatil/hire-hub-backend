package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.User;
import lombok.Data;

@Data
public class CompanyRequest {
    private User user;
    private String companyName;
    private String address;
    private String industry;
    private String companySize;
    private Boolean isBlocked;
    private String website;
    private String description;
}
