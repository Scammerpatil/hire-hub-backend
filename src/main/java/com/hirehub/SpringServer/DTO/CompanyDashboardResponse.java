package com.hirehub.SpringServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDashboardResponse {

    private Stats stats;
    private List<ApplicantsByMonth> applicationsByMonth;
    private JobStatus jobStatus;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stats {
        private long totalJobs;
        private long activeJobs;
        private long totalApplicants;
        private long shortlisted;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplicantsByMonth {
        private String month;
        private long applicants;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobStatus {
        private long active;
        private long closed;
    }
}
