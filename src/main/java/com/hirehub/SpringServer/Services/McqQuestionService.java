package com.hirehub.SpringServer.Services;

import com.hirehub.SpringServer.DTO.McqQuestionResponse;
import com.hirehub.SpringServer.Entity.McqQuestion;

import java.util.List;
import java.util.Optional;

public interface McqQuestionService {

    McqQuestion addQuestion(McqQuestion question);

    McqQuestion updateQuestion(Long questionId, McqQuestion question);

    void blockQuestion(Long questionId);

    void deleteQuestion(Long questionId);

    Optional<McqQuestion> getById(Long questionId);

    List<McqQuestion> getAllActive();

    List<McqQuestionResponse> getMcqsForScreening();
}
