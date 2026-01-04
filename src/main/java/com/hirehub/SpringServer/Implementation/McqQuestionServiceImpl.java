package com.hirehub.SpringServer.Implementation;

import com.hirehub.SpringServer.DTO.McqQuestionResponse;
import com.hirehub.SpringServer.Entity.McqQuestion;
import com.hirehub.SpringServer.Repository.McqQuestionRepository;
import com.hirehub.SpringServer.Services.McqQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class McqQuestionServiceImpl implements McqQuestionService {

    private final McqQuestionRepository mcqQuestionRepository;

    @Override
    public McqQuestion addQuestion(McqQuestion question) {
        question.setActive(true);
        return mcqQuestionRepository.save(question);
    }

    @Override
    public McqQuestion updateQuestion(Long questionId, McqQuestion updated) {

        McqQuestion existing = mcqQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        existing.setCategory(updated.getCategory());
        existing.setQuestionText(updated.getQuestionText());
        existing.setOptionA(updated.getOptionA());
        existing.setOptionB(updated.getOptionB());
        existing.setOptionC(updated.getOptionC());
        existing.setOptionD(updated.getOptionD());
        existing.setCorrectOption(updated.getCorrectOption());

        return mcqQuestionRepository.save(existing);
    }

    @Override
    public void blockQuestion(Long questionId) {
        McqQuestion question = mcqQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setActive(false);
        mcqQuestionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        mcqQuestionRepository.deleteById(questionId);
    }

    @Override
    public Optional<McqQuestion> getById(Long questionId) {
        return mcqQuestionRepository.findById(questionId);
    }

    @Override
    public List<McqQuestion> getAllActive() {
        return mcqQuestionRepository.findByActiveTrue();
    }

    @Override
    public List<McqQuestionResponse> getMcqsForScreening() {
        return List.of();
    }
}
