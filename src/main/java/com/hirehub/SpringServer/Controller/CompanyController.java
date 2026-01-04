package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.*;
import com.hirehub.SpringServer.Entity.*;
import com.hirehub.SpringServer.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CompanyController {

    private final JobPostService jobPostService;
    private final CompanyService companyService;
    private final ApplicationService applicationService;
    private final ReferralService referralService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/get-company/{companyId}")
    public ResponseEntity<Optional<Company>> getSingleCompany(@PathVariable Long companyId){
        Optional<Company> company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/all-companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @PostMapping("/post-job")
    public ResponseEntity<JobPostResponse> postJob(@RequestBody JobPostRequest request) {
        Company company = companyService.getCompanyByUserId(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        JobPost job = new JobPost();
        job.setCompany(company);
        job.setJobTitle(request.getJobTitle());
        job.setJobPosition(request.getJobPosition());
        job.setJobLocation(request.getJobLocation());
        job.setJobCategory(request.getJobCategory());
        job.setJobDescription(request.getJobDescription());
        job.setRequiredDegrees(request.getRequiredDegrees());
        job.setRequiredExperience(request.getRequiredExperience());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setMaxPackage(request.getMaxPackage());
        job.setMinPackage(request.getMinPackage());
        job.setTotalOpenings(request.getTotalOpenings());
        job.setStatus(request.getStatus());
        job.setPostedAt(new Timestamp(System.currentTimeMillis()));

        JobPost saved = jobPostService.createJob(job);

        JobPostResponse response = new JobPostResponse(saved);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-job/{jobId}")
    public ResponseEntity<JobPostResponse> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobPostRequest request
    ) {
        JobPost existingJob = jobPostService.getJobById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        existingJob.setJobTitle(request.getJobTitle());
        existingJob.setJobPosition(request.getJobPosition());
        existingJob.setJobLocation(request.getJobLocation());
        existingJob.setJobCategory(request.getJobCategory());
        existingJob.setJobDescription(request.getJobDescription());
        existingJob.setRequiredDegrees(request.getRequiredDegrees());
        existingJob.setRequiredExperience(request.getRequiredExperience());
        existingJob.setRequiredSkills(request.getRequiredSkills());
        existingJob.setMinPackage(request.getMinPackage());
        existingJob.setMaxPackage(request.getMaxPackage());
        existingJob.setTotalOpenings(request.getTotalOpenings());
        existingJob.setStatus(request.getStatus());
        JobPost updatedJob = jobPostService.updateJob(jobId, existingJob);
        JobPostResponse response = new JobPostResponse(updatedJob);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications/{companyId}")
    public ResponseEntity<List<ApplicationResponse>> getCompanyApplications(
            @PathVariable Long companyId
    ) {
        List<JobPost> jobs = jobPostService.getJobsByCompany(companyId);
        List<Application> applications = new ArrayList<>();

        for (JobPost job : jobs) {
            applications.addAll(applicationService.getApplicationsByJob(job.getJobId()));
        }

        List<ApplicationResponse> responses = applications.stream().map(app -> {
            ApplicationResponse ar = new ApplicationResponse();
            ar.setApplicationId(app.getApplicationId());
            ar.setStatus(app.getStatus());
            return ar;
        }).toList();

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/referrals/{companyId}")
    public ResponseEntity<List<ReferralResponse>> getCompanyReferrals(@PathVariable Long companyId) {

        List<Referral> referrals = referralService.getReferralsByEmployee(companyId);

        List<ReferralResponse> responses = referrals.stream().map(ref -> {
            ReferralResponse rr = new ReferralResponse();
            rr.setReferralId(ref.getReferralId());
            return rr;
        }).toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/register-employee")
    public ResponseEntity<String> registerEmployee(@RequestBody EmployeeRequest employee) {
        try {
            User existingUser = userService.getUserByEmail(employee.getUser().getEmail()).orElse(null);
            if (existingUser != null) {
                return ResponseEntity.badRequest().body("User with this email already exists.");
            }
            Company company = companyService.getCompanyByUserId(employee.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            User newUser = new User();
            newUser.setFullName(employee.getUser().getFullName());
            newUser.setEmail(employee.getUser().getEmail());
            newUser.setPhone(employee.getUser().getPhone());
            newUser.setProfileImage(employee.getUser().getProfileImage());
            newUser.setRole("employee");
            newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            String encryptedPassword = passwordEncoder.encode(employee.getUser().getPassword());
            newUser.setPassword(encryptedPassword);
            User savedUser = userService.createUser(newUser);

            Employee emp = new Employee();
            emp.setUser(savedUser);
            emp.setCompany(company);
            employeeService.createEmployee(emp);
            return ResponseEntity.ok("Employee registered successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/employees/{userId}")
    public ResponseEntity<List<Employee>> getCompanyEmployees(@PathVariable Long userId) {
        Company company = companyService.getCompanyByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        List<Employee> employees = employeeService.getEmployeesByCompanyId(company.getCompanyId());
        System.out.println(employees);
        return ResponseEntity.ok(employees);
    }
}
