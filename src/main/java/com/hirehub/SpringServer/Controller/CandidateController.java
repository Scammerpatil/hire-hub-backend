package com.hirehub.SpringServer.Controller;

import com.hirehub.SpringServer.DTO.CandidateRequest;
import com.hirehub.SpringServer.DTO.CandidateResponse;
import com.hirehub.SpringServer.Entity.Candidate;
import com.hirehub.SpringServer.Entity.User;
import com.hirehub.SpringServer.Services.CandidateService;
import com.hirehub.SpringServer.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidate")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<CandidateResponse> createCandidate(
            @RequestBody CandidateRequest request
    ) {
        User user = new User();
        user.setFullName(request.getUser().getFullName());
        user.setEmail(request.getUser().getEmail());
        user.setPhone(request.getUser().getPhone());
        user.setProfileImage(request.getUser().getProfileImage());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setRole("candidate");
        String encryptedPassword = passwordEncoder.encode(request.getUser().getPassword());
        user.setPassword(encryptedPassword);
        userService.createUser(user);
        Candidate candidate = new Candidate();
        candidate.setUser(user);
        candidate.setDegree(request.getDegree());
        candidate.setExperienceYears(request.getExperienceYears());
        candidate.setSkills(request.getSkills());
        candidate.setResumeUrl(request.getResumeUrl());
        candidate.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Candidate saved = candidateService.createCandidate(candidate);

        CandidateResponse response = new CandidateResponse();
        response.setCandidateId(saved.getCandidateId());
        response.setDegree(saved.getDegree());
        response.setExperienceYears(saved.getExperienceYears());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Optional<Candidate>> getCandidateProfile(@PathVariable Long userId) {
        Optional<Candidate> candidate = candidateService.getCandidateByUserId(userId);
        if (candidate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(candidate);
    }

    @PutMapping("/update")
    public ResponseEntity<Candidate> updateCandidateProfile(
            @RequestBody Candidate candidate
    ) {
        Optional<Candidate> existingCandidateOpt =
                candidateService.getCandidateById(candidate.getCandidateId());
        if (existingCandidateOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Candidate existingCandidate = existingCandidateOpt.get();
        BeanUtils.copyProperties(
                candidate,
                existingCandidate,
                "candidateId", "user.email", "createdAt"
        );
        existingCandidate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Candidate updated = candidateService.createCandidate(existingCandidate);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<Optional<Candidate>> getCandidateById(@PathVariable Long candidateId){
        Optional<Candidate> candidate = candidateService.getCandidateById(candidateId);
        if (candidate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(candidate);
    }
}
