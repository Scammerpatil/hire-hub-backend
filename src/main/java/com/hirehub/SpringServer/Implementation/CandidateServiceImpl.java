package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.Entity.Candidate;
import com.hirehub.SpringServer.Repository.CandidateRepository;
import com.hirehub.SpringServer.Services.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Optional<Candidate> getCandidateById(Long candidateId) {
        return candidateRepository.findById(candidateId);
    }

    @Override
    public Optional<Candidate> getCandidateByUserId(Long userId) {
        return candidateRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
}
