package com.hirehub.SpringServer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "candidateId")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private JobPost job;

    private String status;

    private Timestamp createdAt;
}
