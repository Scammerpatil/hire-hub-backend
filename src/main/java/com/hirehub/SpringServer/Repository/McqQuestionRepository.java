package com.hirehub.SpringServer.Repository;

import com.hirehub.SpringServer.Entity.McqQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface McqQuestionRepository extends JpaRepository<McqQuestion, Long> {
    @Query(value = """
    SELECT * FROM mcq_questions
    WHERE active = true
    ORDER BY RAND()
    LIMIT 10
""", nativeQuery = true)
    List<McqQuestion> findRandomMcqs();

    List<McqQuestion> findByActiveTrue();
}