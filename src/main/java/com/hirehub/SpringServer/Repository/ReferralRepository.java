package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    long countByEmployee_EmployeeId(Long employeeId);

    long countByEmployee_User_UserId(Long userId);

    @Query("""
    SELECT COUNT(l)
    FROM ApplicationStatusLog l
    WHERE l.application.job.jobId IN (
        SELECT r.job.jobId
        FROM Referral r
        WHERE r.employee.user.userId = :userId
    )
    AND l.toStatus = 'SHORTLISTED'
    """)
    long countShortlistedReferrals(@Param("userId") Long userId);

    @Query("""
    SELECT MONTH(r.referralDate), COUNT(r)
    FROM Referral r
    WHERE r.employee.user.userId = :userId
    GROUP BY MONTH(r.referralDate)
    ORDER BY MONTH(r.referralDate)
    """)
    List<Object[]> countReferralsByMonth(@Param("userId") Long userId);

    @Query("""
    SELECT MONTH(r.referralDate), COUNT(r)
    FROM Referral r
    WHERE r.employee.user.userId = :userId
    GROUP BY MONTH(r.referralDate)
    ORDER BY MONTH(r.referralDate)
    """)
    List<Object[]> countEmployeeReferralsByMonth(@Param("userId") Long userId);
}
