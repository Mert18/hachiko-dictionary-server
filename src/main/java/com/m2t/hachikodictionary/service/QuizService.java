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
import java.util.Objects;

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
        quizRepository.save(quiz);
        logger.info("{} completed quiz with {} correct answers, {} incorrect answers and {} not answered.",
                quiz.getAccount().getUsername(), quiz.getCorrectAnswers(), quiz.getIncorrectAnswers(), quiz.getNotAnswered());
        return new Response(true, "Quiz completed successfully.", false);
    }

    public Response generateQuiz(String difficulty) {
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

        NewQuiz newQuiz = new NewQuiz(quizQuestions, difficulty);
        logger.info("Quiz generated with difficulty {}.", difficulty);

        return new Response(true, "Quiz generated successfully.", newQuiz, false);
    }

    public Response getAccountQuizzes(Account account) {
        List<Quiz> quizzes =  quizRepository.findAllByAccountId(account.getId());
        int gameCount = quizzes.size();
        int correctAnswers = quizzes.stream().mapToInt(Quiz::getCorrectAnswers).sum();
        int incorrectAnswers = quizzes.stream().mapToInt(Quiz::getIncorrectAnswers).sum();
        int notAnswered = quizzes.stream().mapToInt(Quiz::getNotAnswered).sum();
        int totalQuestions = correctAnswers + incorrectAnswers + notAnswered;

        QuizResponse quizResponse = new QuizResponse(Objects.requireNonNull(account.getId()), gameCount, correctAnswers, incorrectAnswers, notAnswered, totalQuestions);

        return new Response(true, "Quizzes retrieved successfully.", quizResponse, false);
    }
}
