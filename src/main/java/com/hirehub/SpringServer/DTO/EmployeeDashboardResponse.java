package com.hirehub.SpringServer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeDashboardResponse {

    private Stats stats;
    private List<ReferralsByMonth> referralsByMonth;

    @Data
    @AllArgsConstructor
    public static class Stats {
        private long referralsMade;
        private long shortlisted;
    }

    @Data
    @AllArgsConstructor
    public static class ReferralsByMonth {
        private String month;
        private long count;
    }
}
