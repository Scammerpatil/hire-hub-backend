package com.hirehub.SpringServer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private String degree;
    private Integer experienceYears;

    @Column(columnDefinition = "TEXT")
    private String skills;

    private String resumeUrl;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(columnDefinition = "TEXT")
    private String certifications;

    @Column(columnDefinition = "TEXT")
    private String projects;

    private String currentJobTitle;
    private String currentCompany;
    private Integer currentSalary;
    private Integer expectedSalary;
    private String noticePeriod;

    private String resumeHeadline;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;

    @Column(columnDefinition = "TEXT")
    private String preferredLocations;

    private String employmentType;
    private String workType;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Candidate(Candidate c){
        this.candidateId = c.candidateId;
        this.user = c.user;
        this.degree = c.degree;
        this.experienceYears = c.experienceYears;
        this.skills = c.skills;
        this.resumeUrl = c.resumeUrl;
        this.education = c.education;
        this.experience = c.experience;
        this.certifications = c.certifications;
        this.projects = c.projects;
        this.currentJobTitle = c.currentJobTitle;
        this.currentCompany = c.currentCompany;
        this.currentSalary = c.currentSalary;
        this.expectedSalary = c.expectedSalary;
        this.noticePeriod = c.noticePeriod;
        this.resumeHeadline = c.resumeHeadline;
        this.summary = c.summary;
        this.linkedinUrl = c.linkedinUrl;
        this.githubUrl = c.githubUrl;
        this.portfolioUrl = c.portfolioUrl;
        this.preferredLocations = c.preferredLocations;
        this.employmentType = c.employmentType;
        this.workType = c.workType;
        this.createdAt = c.createdAt;
        this.updatedAt = c.updatedAt;
    }
}
