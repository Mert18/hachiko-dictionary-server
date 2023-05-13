package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.QuizResponse;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Quiz;
import com.m2t.hachikodictionary.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Response getAccountQuizzes(Account account) {
        try {
            List<Quiz> quizzes =  quizRepository.findAllByAccountId(account.getId());
            int gameCount = quizzes.size();
            int correctAnswers = quizzes.stream().mapToInt(Quiz::getCorrectAnswers).sum();
            int incorrectAnswers = quizzes.stream().mapToInt(Quiz::getIncorrectAnswers).sum();

            QuizResponse quizResponse = new QuizResponse(account.getId(), gameCount, correctAnswers, incorrectAnswers);

            return new Response(true, "Quizzes retrieved successfully.", quizResponse);
        } catch (Exception e) {
            logger.error("Error getting quizzes", e);
            return new Response(false, "Error getting quizzes.");
        }
    }
}
