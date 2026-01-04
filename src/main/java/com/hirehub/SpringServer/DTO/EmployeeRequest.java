package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.User;
import lombok.Data;

@Data
public class EmployeeRequest {
    private Long userId;
    private Long companyId;
    private User user;
}
