package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.ApplicationStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationStatusLogRepository extends JpaRepository<ApplicationStatusLog, Long> {

    List<ApplicationStatusLog> findByApplication_ApplicationId(Long applicationId);
}
