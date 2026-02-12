package com.hirehub.SpringServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private Stats stats;
    private List<JobsByMonth> jobsByMonth;
    private JobDistribution jobDistribution;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stats {
        private long totalCompanies;
        private long totalApplicants;
        private long totalJobs;
        private long totalActiveJobs;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobsByMonth {
        private String month;
        private long jobs;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JobDistribution {
        private long active;
        private long closed;
    }
}
