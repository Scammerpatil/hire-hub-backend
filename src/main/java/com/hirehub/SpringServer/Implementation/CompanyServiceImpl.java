package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.Entity.Company;
import com.hirehub.SpringServer.Repository.CompanyRepository;
import com.hirehub.SpringServer.Services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> getCompanyById(Long companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public Optional<Company> getCompanyByUserId(Long userId) {
        return companyRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public List<Company> getBlockedCompanies() {
        return companyRepository.findByIsBlocked(true);
    }
}
