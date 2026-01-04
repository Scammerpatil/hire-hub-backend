package com.hirehub.SpringServer.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "jobPosts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    private String jobTitle;
    private String jobPosition;
    private String jobLocation;
    private String jobCategory;
    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    private String requiredDegrees;
    private Integer requiredExperience;

    @Column(columnDefinition = "TEXT")
    private String requiredSkills;

    private Double minPackage;
    private Double maxPackage;
    private Integer totalOpenings;

    private String status;

    private Timestamp postedAt;
}
