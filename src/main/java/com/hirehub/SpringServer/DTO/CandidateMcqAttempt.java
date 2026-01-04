package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.Candidate;
import com.hirehub.SpringServer.Entity.JobPost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "candidate_mcq_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateMcqAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attemptId;

    @ManyToOne
    private Candidate candidate;

    @ManyToOne
    private JobPost job;

    private Integer totalQuestions; // 10
    private Integer correctAnswers;

    private Boolean passed;

    private Timestamp startedAt;
    private Timestamp submittedAt;
}
