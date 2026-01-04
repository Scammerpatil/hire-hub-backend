package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.DTO.McqAnswerRequest;
import com.hirehub.SpringServer.DTO.McqQuestionResponse;
import com.hirehub.SpringServer.Entity.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    Application createApplication(Application application);
    Optional<Application> getApplicationById(Long applicationId);
    List<Application> getApplicationsByCandidate(Long candidateId);
    List<Application> getApplicationsByJob(Long jobId);
    boolean hasAlreadyApplied(Long candidateId, Long jobId);
    String isEligible(Long candidateId, Long jobId);

    List<McqQuestionResponse> getMcqsForScreening();
    boolean validateMcqs(List<McqAnswerRequest> answers);
}
