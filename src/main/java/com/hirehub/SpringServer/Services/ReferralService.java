package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.Referral;

import java.util.List;
import java.util.Optional;

public interface ReferralService {
    Referral createReferral(Referral referral);
    List<Referral> getReferralsByEmployee(Long employeeId);
    Optional<Referral> getReferralByCandidateAndJob(Long candidateId, Long jobId);
    List<Referral> getReferralsByCandidate(Long candidateId);
    List<Referral> getReferralsByJob(Long jobId);
}
