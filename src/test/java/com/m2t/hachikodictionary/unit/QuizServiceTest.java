package com.m2t.hachikodictionary.unit;

import com.m2t.hachikodictionary.repository.QuizRepository;
import com.m2t.hachikodictionary.repository.WordRepository;
import com.m2t.hachikodictionary.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QuizServiceTest {
    private QuizService service;
    private QuizRepository quizRepository;
    private WordRepository wordRepository;

    @BeforeEach
    public void setUp() {
        quizRepository = Mockito.mock(QuizRepository.class);
        wordRepository = Mockito.mock(WordRepository.class);
        service = new QuizService(quizRepository, wordRepository);
    }
}
