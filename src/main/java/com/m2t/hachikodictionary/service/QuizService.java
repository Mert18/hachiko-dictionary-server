package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.model.Quiz;
import com.m2t.hachikodictionary.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Response completeQuiz(Quiz quiz) {
        try {
            quizRepository.save(quiz);
            return new Response(true, "Quiz completed successfully");
        } catch (Exception e) {
            logger.error("Error creating quiz", e);
            return new Response(false, "Error creating quiz.");
        }
    }
}
