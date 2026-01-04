package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.Entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Company createCompany(Company company);
    Optional<Company> getCompanyById(Long companyId);
    Optional<Company> getCompanyByUserId(Long userId);
    List<Company> getAllCompanies();
    List<Company> getBlockedCompanies();
}
