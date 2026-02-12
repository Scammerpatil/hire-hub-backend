package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidate_CandidateId(Long candidateId);

    List<Application> findByJob_JobId(Long jobId);

    List<Application> findByStatus(String status);

    boolean existsByCandidateCandidateIdAndJobJobId(Long candidateId, Long jobId);
    @Query("""
    SELECT COUNT(a)
    FROM Application a
    WHERE a.job.company.companyId = :companyId
    """)
    long countByCompany(Long companyId);

    @Query("""
    SELECT COUNT(a)
    FROM Application a
    WHERE a.job.company.companyId = :companyId
    AND a.status = 'SHORTLISTED'
    """)
    long countShortlistedByCompany(Long companyId);

    @Query("""
    SELECT COUNT(r)
    FROM Referral r
    WHERE r.employee.user.userId = :userId
    """)
    long countReferralsByEmployee(@Param("userId") Long userId);

    long countByCandidate_User_UserId(Long userId);

    @Query("""
    SELECT MONTH(a.createdAt), COUNT(a)
    FROM Application a
    WHERE a.candidate.user.userId = :userId
    GROUP BY MONTH(a.createdAt)
    ORDER BY MONTH(a.createdAt)
    """)
    List<Object[]> countCandidateApplicationsByMonth(@Param("userId") Long userId);

}

