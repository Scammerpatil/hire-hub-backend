package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByIsBlocked(boolean isBlocked);

    Optional<Company> findByUser_UserId(Long userId);
}
