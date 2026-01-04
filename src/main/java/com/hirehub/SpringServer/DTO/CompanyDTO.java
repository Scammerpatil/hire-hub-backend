package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class CompanyDTO {
    private Long companyId;
    private String companyName;
    private String industry;
    private String companySize;
}
