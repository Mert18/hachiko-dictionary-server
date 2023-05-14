package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.QuizResponse;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.model.*;
import com.m2t.hachikodictionary.repository.QuizRepository;
import com.m2t.hachikodictionary.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepository quizRepository;
    private final WordRepository wordRepository;

    public QuizService(QuizRepository quizRepository, WordRepository wordRepository) {
        this.quizRepository = quizRepository;
        this.wordRepository = wordRepository;
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

    public Response generateQuiz(String difficulty) {
        try {
            List<QuizQuestion> quizQuestions = new ArrayList<>();

            while(quizQuestions.size() < 10) {
                Word newWord = wordRepository.findRandomWordByDifficulty(difficulty);

                List<String> choices = new ArrayList<>(4);
                choices.add(newWord.getTitle());
                for(int i=0; i<3; i++) {
                    String choice = wordRepository.findRandomWordByDifficulty(difficulty).getTitle();

                    while(choices.contains(choice)) {
                        choice = wordRepository.findRandomWordByDifficulty(difficulty).getTitle();
                    }
                    choices.add(choice);
                }
                Collections.shuffle(choices);
                QuizQuestion newQuizQuestion = new QuizQuestion(newWord.getDescriptions().iterator().next(), newWord.getTitle(), choices);
                quizQuestions.add(newQuizQuestion);
            }
            System.out.println("new quiz questions: " + quizQuestions);
            NewQuiz newQuiz = new NewQuiz(quizQuestions, difficulty);
            return new Response(true, "Quiz generated successfully.", newQuiz);
        } catch (Exception e) {
            logger.error("Error generating quiz", e);
            return new Response(false, "Error generating quiz.");
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
