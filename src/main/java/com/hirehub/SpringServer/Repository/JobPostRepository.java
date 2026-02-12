package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    List<JobPost> findByCompany_CompanyId(Long companyId);

    List<JobPost> findByStatus(String status);

    List<JobPost> findByCompanyUserUserId(Long userId);
    @Query("""
    SELECT MONTH(j.postedAt), COUNT(j)
    FROM JobPost j
    GROUP BY MONTH(j.postedAt)
    ORDER BY MONTH(j.postedAt)
    """)
    List<Object[]> countJobsByMonth();

    long countByStatus(String status);

    long countByCompany_CompanyId(Long companyId);

    long countByCompany_CompanyIdAndStatus(Long companyId, String status);

    @Query("""
        SELECT MONTH(a.createdAt), COUNT(a)
        FROM Application a
        WHERE a.job.company.companyId = :companyId
        GROUP BY MONTH(a.createdAt)
        ORDER BY MONTH(a.createdAt)
    """)
    List<Object[]> countApplicationsByMonth(Long companyId);

}
