package com.hirehub.SpringServer.DTO;

import com.hirehub.SpringServer.Entity.McqQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidate_mcq_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateMcqAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CandidateMcqAttempt attempt;

    @ManyToOne
    private McqQuestion question;

    private Character selectedOption;
}