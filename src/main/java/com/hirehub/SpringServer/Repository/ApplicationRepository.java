package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidate_CandidateId(Long candidateId);

    List<Application> findByJob_JobId(Long jobId);

    List<Application> findByStatus(String status);

    boolean existsByCandidateCandidateIdAndJobJobId(Long candidateId, Long jobId);
}

