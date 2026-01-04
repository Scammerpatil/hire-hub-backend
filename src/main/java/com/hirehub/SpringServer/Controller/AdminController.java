package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.CompanyRequest;
import com.hirehub.SpringServer.DTO.CompanyResponse;
import com.hirehub.SpringServer.DTO.UserRequest;
import com.hirehub.SpringServer.Entity.Company;
import com.hirehub.SpringServer.Entity.JobPost;
import com.hirehub.SpringServer.Entity.McqQuestion;
import com.hirehub.SpringServer.Entity.User;
import com.hirehub.SpringServer.Services.CompanyService;
import com.hirehub.SpringServer.Services.JobPostService;
import com.hirehub.SpringServer.Services.McqQuestionService;
import com.hirehub.SpringServer.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CompanyService companyService;
    private final JobPostService jobPostService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final McqQuestionService mcqQuestionService;

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody UserRequest request) {

        if (!"admin".equalsIgnoreCase(request.getRole())) {
            return ResponseEntity.badRequest().body("Role must be admin");
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User admin = new User();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setRole("admin");
        admin.setPhone(request.getPhone());
        admin.setProfileImage(request.getProfileImage());
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        admin.setPassword(encryptedPassword);
        admin.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userService.createUser(admin);

        return ResponseEntity.ok("Admin created successfully");
    }

    @PostMapping("/register-company")
    public ResponseEntity<CompanyResponse> registerCompany(@RequestBody CompanyRequest request) {
        if (userService.existsByEmail(request.getUser().getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        User companyUser = new User();
        companyUser.setFullName(request.getUser().getFullName());
        companyUser.setEmail(request.getUser().getEmail());
        companyUser.setPhone(request.getUser().getPhone());
        companyUser.setProfileImage(request.getUser().getProfileImage());
        String encryptedPassword = passwordEncoder.encode(request.getUser().getPassword());
        companyUser.setPassword(encryptedPassword);
        companyUser.setRole("company");
        companyUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        User savedUser = userService.createUser(companyUser);

        Company company = new Company();
        company.setUser(savedUser);
        company.setAddress(request.getAddress());
        company.setIndustry(request.getIndustry());
        company.setCompanySize(request.getCompanySize());
        company.setIsBlocked(false);
        company.setWebsite(request.getWebsite());
        company.setDescription(request.getDescription());
        company.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Company saved = companyService.createCompany(company);

        CompanyResponse response = new CompanyResponse();
        response.setCompanyId(saved.getCompanyId());
        response.setIndustry(saved.getIndustry());
        response.setCompanySize(saved.getCompanySize());
        response.setCreatedAt(saved.getCreatedAt());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/block-company/{companyId}")
    public ResponseEntity<String> blockCompany(@PathVariable Long companyId) {

        Company company = companyService.getCompanyById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setIsBlocked(true);
        companyService.createCompany(company);

        return ResponseEntity.ok("Company blocked successfully");
    }

    @PutMapping("/block-job/{jobId}")
    public ResponseEntity<String> blockJob(@PathVariable Long jobId) {

        JobPost job = jobPostService.getJobById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus("blocked");
        jobPostService.createJob(job);

        return ResponseEntity.ok("Job blocked successfully");
    }

    @GetMapping("/mcq/all")
    public ResponseEntity<?> getAllMcqs() {
        return ResponseEntity.ok(mcqQuestionService.getAllActive());
    }

    @PostMapping("/mcq/add")
    public ResponseEntity<?> addMcq(@RequestBody McqQuestion question) {
        return ResponseEntity.ok(mcqQuestionService.addQuestion(question));
    }

    @PutMapping("/mcq/update/{questionId}")
    public ResponseEntity<?> updateMcq(
            @PathVariable Long questionId,
            @RequestBody McqQuestion question
    ) {
        return ResponseEntity.ok(
                mcqQuestionService.updateQuestion(questionId, question)
        );
    }

    @PutMapping("/mcq/block/{questionId}")
    public ResponseEntity<String> blockMcq(@PathVariable Long questionId) {
        mcqQuestionService.blockQuestion(questionId);
        return ResponseEntity.ok("MCQ question blocked successfully");
    }

    @DeleteMapping("/mcq/delete/{questionId}")
    public ResponseEntity<String> deleteMcq(@PathVariable Long questionId) {
        mcqQuestionService.deleteQuestion(questionId);
        return ResponseEntity.ok("MCQ question deleted permanently");
    }
}


