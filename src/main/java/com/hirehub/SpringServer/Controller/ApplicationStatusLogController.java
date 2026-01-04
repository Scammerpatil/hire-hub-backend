package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.ApplicationStatusLogResponse;
import com.hirehub.SpringServer.Entity.ApplicationStatusLog;
import com.hirehub.SpringServer.Services.ApplicationStatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-status")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ApplicationStatusLogController {
    private final ApplicationStatusLogService statusLogService;

    @PutMapping("/update/{applicationId}")
    public ResponseEntity<ApplicationStatusLogResponse> updateStatus(
            @PathVariable Long applicationId,
            @RequestParam String newStatus
    ) {
        ApplicationStatusLog log =
                statusLogService.updateApplicationStatus(applicationId, newStatus);

        ApplicationStatusLogResponse response = new ApplicationStatusLogResponse();
        response.setLogId(log.getLogId());
        response.setFromStatus(log.getFromStatus());
        response.setToStatus(log.getToStatus());
        response.setUpdatedAt(log.getUpdatedAt());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<List<ApplicationStatusLogResponse>> getLogs(
            @PathVariable Long applicationId
    ) {
        List<ApplicationStatusLogResponse> resp =
                statusLogService.getLogsByApplication(applicationId)
                        .stream()
                        .map(l -> {
                            ApplicationStatusLogResponse r = new ApplicationStatusLogResponse();
                            r.setLogId(l.getLogId());
                            r.setFromStatus(l.getFromStatus());
                            r.setToStatus(l.getToStatus());
                            r.setUpdatedAt(l.getUpdatedAt());
                            return r;
                        }).toList();

        return ResponseEntity.ok(resp);
    }
}
