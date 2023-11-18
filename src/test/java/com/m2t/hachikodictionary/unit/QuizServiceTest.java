package com.m2t.hachikodictionary.unit;

import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Quiz;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.QuizRepository;
import com.m2t.hachikodictionary.repository.WordRepository;
import com.m2t.hachikodictionary.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void testCompleteQuiz_whenSuccessful_shouldReturnSuccessfulResponse() {
        // Arrange
        Account account = new Account();
        Quiz quiz = new Quiz();
        quiz.setAccount(account);
        quiz.setCorrectAnswers(4);
        quiz.setIncorrectAnswers(2);
        quiz.setNotAnswered(4);
        quiz.setDifficulty("medium");

        Response expectedResponse = new Response(true, "Quiz completed successfully.", false);

        // Act
        Response response = service.completeQuiz(quiz);

        // Assert
        assertEquals(expectedResponse.getData(), response.getData());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertEquals(expectedResponse.getSuccess(), response.getSuccess());

        verify(quizRepository, times(1)).save(quiz);
    }

}
