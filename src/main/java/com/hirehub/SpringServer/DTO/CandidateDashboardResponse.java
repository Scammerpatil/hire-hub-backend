package com.hirehub.SpringServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDashboardResponse {

    private Stats stats;
    private List<ApplicationsByMonth> applicationsByMonth;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Stats {
        private long jobsApplied;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplicationsByMonth {
        private String month;
        private long count;
    }
}
