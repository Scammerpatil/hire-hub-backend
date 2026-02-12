package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.AdminDashboardResponse;
import com.hirehub.SpringServer.DTO.CandidateDashboardResponse;
import com.hirehub.SpringServer.DTO.CompanyDashboardResponse;
import com.hirehub.SpringServer.DTO.EmployeeDashboardResponse;
import com.hirehub.SpringServer.Services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    @Autowired
    private final DashboardService dashboardService;

    @GetMapping("/admin")
    public ResponseEntity<AdminDashboardResponse> getAdminDashboard() {
        return ResponseEntity.ok(dashboardService.getAdminDashboard());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<CompanyDashboardResponse> getCompanyDashboard(
            @PathVariable Long companyId
    ) {
        return ResponseEntity.ok(
                dashboardService.getCompanyDashboard(companyId)
        );
    }

    @GetMapping("/employee/{userId}")
    public ResponseEntity<EmployeeDashboardResponse> getEmployeeDashboard(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                dashboardService.getEmployeeDashboard(userId)
        );
    }

    @GetMapping("/candidate/{userId}")
    public ResponseEntity<CandidateDashboardResponse> candidateDashboard(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                dashboardService.getCandidateDashboard(userId)
        );
    }


}