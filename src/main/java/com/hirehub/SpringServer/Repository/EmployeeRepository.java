package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser_UserId(Long userId);

    List<Employee> findByCompany_CompanyId(Long companyId);
}
