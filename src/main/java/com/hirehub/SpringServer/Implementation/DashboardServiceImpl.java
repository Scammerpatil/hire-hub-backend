package com.hirehub.SpringServer.Implementation;
import com.hirehub.SpringServer.DTO.*;
import com.hirehub.SpringServer.Entity.Employee;
import com.hirehub.SpringServer.Repository.*;
import com.hirehub.SpringServer.Services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final CompanyRepository companyRepository;
    private final CandidateRepository candidateRepository;
    private final JobPostRepository jobPostRepository;
    private final ApplicationRepository applicationRepository;
    private final ReferralRepository referralRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AdminDashboardResponse getAdminDashboard() {

        long totalCompanies = companyRepository.count();
        long totalApplicants = candidateRepository.count();
        long totalJobs = jobPostRepository.count();
        long activeJobs = jobPostRepository.countByStatus("ACTIVE");
        long closedJobs = totalJobs - activeJobs;

        List<Object[]> jobCounts = jobPostRepository.countJobsByMonth();

        List<AdminDashboardResponse.JobsByMonth> jobsByMonth =
                jobCounts.stream()
                        .map(row -> new AdminDashboardResponse.JobsByMonth(
                                Month.of(((Number) row[0]).intValue())
                                        .name().substring(0, 3),
                                ((Number) row[1]).longValue()
                        ))
                        .toList();

        return new AdminDashboardResponse(
                new AdminDashboardResponse.Stats(
                        totalCompanies,
                        totalApplicants,
                        totalJobs,
                        activeJobs
                ),
                jobsByMonth,
                new AdminDashboardResponse.JobDistribution(
                        activeJobs,
                        closedJobs
                )
        );
    }
    @Override
    public CompanyDashboardResponse getCompanyDashboard(Long userId) {
        Long companyId = companyRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Company not found"))
                .getCompanyId();
        long totalJobs = jobPostRepository.countByCompany_CompanyId(companyId);
        long activeJobs = jobPostRepository.countByCompany_CompanyIdAndStatus(companyId, "ACTIVE");
        long closedJobs = totalJobs - activeJobs;

        long totalApplicants = applicationRepository.countByCompany(companyId);
        long shortlisted = applicationRepository.countShortlistedByCompany(companyId);

        List<Object[]> appStats =
                jobPostRepository.countApplicationsByMonth(companyId);

        List<CompanyDashboardResponse.ApplicantsByMonth> applicationsByMonth =
                appStats.stream()
                        .map(row -> new CompanyDashboardResponse.ApplicantsByMonth(
                                Month.of(((Number) row[0]).intValue())
                                        .name().substring(0, 3),
                                ((Number) row[1]).longValue()
                        ))
                        .toList();

        return new CompanyDashboardResponse(
                new CompanyDashboardResponse.Stats(
                        totalJobs,
                        activeJobs,
                        totalApplicants,
                        shortlisted
                ),
                applicationsByMonth,
                new CompanyDashboardResponse.JobStatus(
                        activeJobs,
                        closedJobs
                )
        );
    }

    @Override
    public EmployeeDashboardResponse getEmployeeDashboard(Long userId) {

        long referralsMade =
                referralRepository.countByEmployee_User_UserId(userId);

        long shortlisted =
                referralRepository.countShortlistedReferrals(userId);

        List<Object[]> referralStats =
                referralRepository.countReferralsByMonth(userId);

        List<EmployeeDashboardResponse.ReferralsByMonth> referralsByMonth =
                referralStats.stream()
                        .map(row -> new EmployeeDashboardResponse.ReferralsByMonth(
                                Month.of(((Number) row[0]).intValue())
                                        .name().substring(0, 3),
                                ((Number) row[1]).longValue()
                        ))
                        .toList();

        return new EmployeeDashboardResponse(
                new EmployeeDashboardResponse.Stats(
                        referralsMade,
                        shortlisted
                ),
                referralsByMonth
        );
    }

    @Override
    public CandidateDashboardResponse getCandidateDashboard(Long userId) {

        long jobsApplied =
                applicationRepository.countByCandidate_User_UserId(userId);

        List<Object[]> rows =
                applicationRepository.countCandidateApplicationsByMonth(userId);

        List<CandidateDashboardResponse.ApplicationsByMonth> applicationsByMonth =
                rows.stream()
                        .map(r -> new CandidateDashboardResponse.ApplicationsByMonth(
                                Month.of(((Number) r[0]).intValue())
                                        .name().substring(0, 3),
                                ((Number) r[1]).longValue()
                        ))
                        .toList();

        return new CandidateDashboardResponse(
                new CandidateDashboardResponse.Stats(jobsApplied),
                applicationsByMonth
        );
    }
}
