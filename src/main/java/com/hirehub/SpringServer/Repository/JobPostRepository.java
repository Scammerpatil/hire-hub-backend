package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    List<JobPost> findByCompany_CompanyId(Long companyId);

    List<JobPost> findByStatus(String status);

    List<JobPost> findByCompanyUserUserId(Long userId);
}
