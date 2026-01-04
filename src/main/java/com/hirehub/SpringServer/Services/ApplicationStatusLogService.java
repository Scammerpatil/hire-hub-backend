package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.ApplicationStatusLog;

import java.util.List;

public interface ApplicationStatusLogService {
    ApplicationStatusLog updateApplicationStatus(Long applicationId,String newStatus);
    List<ApplicationStatusLog> getLogsByApplication(Long applicationId);
}
