package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee createEmployee(Employee employee);
    Optional<Employee> getEmployeeById(Long employeeId);
    Optional<Employee> getEmployeeByUserId(Long userId);
    List<Employee> getEmployeesByCompanyId(Long companyId);
}
