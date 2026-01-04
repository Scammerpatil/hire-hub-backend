package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {
    Candidate createCandidate(Candidate candidate);
    Optional<Candidate> getCandidateById(Long candidateId);
    Optional<Candidate> getCandidateByUserId(Long userId);
    List<Candidate> getAllCandidates();
}
