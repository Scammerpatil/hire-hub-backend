package com.hirehub.SpringServer.DTO;

import lombok.Data;

@Data
public class EmployeeResponse {
    private Long employeeId;
    private UserDTO user;
    private CompanyDTO company;
}
