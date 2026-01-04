package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.Entity.Application;
import com.hirehub.SpringServer.Entity.ApplicationStatusLog;
import com.hirehub.SpringServer.Repository.ApplicationRepository;
import com.hirehub.SpringServer.Repository.ApplicationStatusLogRepository;
import com.hirehub.SpringServer.Services.ApplicationStatusLogService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationStatusLogServiceImpl implements ApplicationStatusLogService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusLogRepository statusLogRepository;

    @Override
    @Transactional
    public ApplicationStatusLog updateApplicationStatus(
            Long applicationId,
            String newStatus
    ) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        String oldStatus = application.getStatus();

        // Update application status
        application.setStatus(newStatus);
        applicationRepository.save(application);

        // Create log
        ApplicationStatusLog log = new ApplicationStatusLog();
        log.setApplication(application);
        log.setFromStatus(oldStatus);
        log.setToStatus(newStatus);
        log.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return statusLogRepository.save(log);
    }

    @Override
    public List<ApplicationStatusLog> getLogsByApplication(Long applicationId) {
        return statusLogRepository.findByApplication_ApplicationId(applicationId);
    }
}
