package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReferralRepository extends JpaRepository<Referral, Long> {

    List<Referral> findByEmployee_EmployeeId(Long employeeId);

    List<Referral> findByReferredCandidate_CandidateId(Long candidateId);

    List<Referral> findByJob_JobId(Long jobId);

    Optional<Referral> findByReferredCandidate_CandidateIdAndJob_JobId(
            Long candidateId,
            Long jobId
    );
}
