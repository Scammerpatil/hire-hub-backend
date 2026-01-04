package com.hirehub.SpringServer.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mcq_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String category; // java, oops, react, sql

    @Column(columnDefinition = "TEXT")
    private String questionText;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private Character correctOption;

    private Boolean active; // admin can disable questions
}
