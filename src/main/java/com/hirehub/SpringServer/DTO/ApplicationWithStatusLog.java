package com.hirehub.SpringServer.DTO;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ApplicationWithStatusLog {

    private Long applicationId;
    private String status;
    private Timestamp appliedAt;

    // Job details
    private JobPostDTO jobPostDTO;

    // Company details
    private Long companyId;
    private String companyName;

    // Status timeline
    private List<ApplicationStatusLogResponse> statusLogs;
}
