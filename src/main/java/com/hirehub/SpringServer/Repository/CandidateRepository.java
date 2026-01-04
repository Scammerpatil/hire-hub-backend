package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUser_UserId(Long userId);

    List<Candidate> findByDegree(String degree);

    Optional<Candidate> findByCandidateId(Long candidateId);
}
