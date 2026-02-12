package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.DTO.AdminDashboardResponse;
import com.hirehub.SpringServer.DTO.CandidateDashboardResponse;
import com.hirehub.SpringServer.DTO.CompanyDashboardResponse;
import com.hirehub.SpringServer.DTO.EmployeeDashboardResponse;

public interface DashboardService {
    AdminDashboardResponse getAdminDashboard();
    CompanyDashboardResponse getCompanyDashboard(Long companyId);
    EmployeeDashboardResponse getEmployeeDashboard(Long userId);
    CandidateDashboardResponse getCandidateDashboard(Long userId);
}
