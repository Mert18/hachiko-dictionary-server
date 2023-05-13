package com.m2t.hachikodictionary.dto;


import com.m2t.hachikodictionary.model.Quiz;
import com.m2t.hachikodictionary.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class QuizDtoConverter {
    public QuizDto quizDtoConverter(Quiz from) {
        return new QuizDto(from.getAccount(), from.getCorrectAnswers(), from.getIncorrectAnswers(), from.getDifficulty());
    }

    public Quiz dtoQuizConverter(QuizDto from) {
        return new Quiz(from.getAccount(), from.getCorrectAnswers(), from.getIncorrectAnswers(), from.getDifficulty());
    }
}
