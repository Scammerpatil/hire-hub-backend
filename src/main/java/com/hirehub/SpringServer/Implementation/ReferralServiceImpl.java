package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.Entity.Referral;
import com.hirehub.SpringServer.Repository.ReferralRepository;
import com.hirehub.SpringServer.Services.ReferralService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferralServiceImpl implements ReferralService {

    private final ReferralRepository referralRepository;

    @Override
    public Referral createReferral(Referral referral) {
        return referralRepository.save(referral);
    }

    @Override
    public List<Referral> getReferralsByEmployee(Long employeeId) {
        return referralRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Override
    public List<Referral> getReferralsByCandidate(Long candidateId) {
        return referralRepository.findByReferredCandidate_CandidateId(candidateId);
    }

    @Override
    public List<Referral> getReferralsByJob(Long jobId) {
        return referralRepository.findByJob_JobId(jobId);
    }

    @Override
    public Optional<Referral> getReferralByCandidateAndJob(
            Long candidateId,
            Long jobId
    ) {
        return referralRepository
                .findByReferredCandidate_CandidateIdAndJob_JobId(candidateId, jobId);
    }
}
