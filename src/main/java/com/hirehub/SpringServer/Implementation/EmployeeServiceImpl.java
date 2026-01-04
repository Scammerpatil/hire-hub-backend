package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.Entity.Employee;
import com.hirehub.SpringServer.Repository.EmployeeRepository;
import com.hirehub.SpringServer.Services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Optional<Employee> getEmployeeByUserId(Long userId) {
        return employeeRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employeeRepository.findByCompany_CompanyId(companyId);
    }
}
