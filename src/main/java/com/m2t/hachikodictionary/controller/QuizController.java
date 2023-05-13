package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.CompleteQuizRequest;
import com.m2t.hachikodictionary.dto.QuizDto;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Quiz;
import com.m2t.hachikodictionary.service.AccountService;
import com.m2t.hachikodictionary.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;
    private final AccountService accountService;

    private final JWTService jwtService;

    public QuizController(QuizService quizService, AccountService accountService, JWTService jwtService) {
        this.quizService = quizService;
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @PostMapping("/complete")
    public ResponseEntity<Response> completeQuiz(@RequestBody CompleteQuizRequest completeQuizRequest, @RequestHeader("Authorization") String token) {
        try {
            System.out.println("completeQuizRequest: " + completeQuizRequest);
            String jwtToken = token.substring(7);
            String accountId = jwtService.extractAccountId(jwtToken);
            System.out.println("accountId: " + accountId);
            Account account = accountService.findAccountById(accountId);

            Quiz quiz = new Quiz(account, completeQuizRequest.getCorrectAnswers(), completeQuizRequest.getIncorrectAnswers(), completeQuizRequest.getDifficulty());

            return ResponseEntity.ok(quizService.completeQuiz(quiz));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Response(false, e.getMessage()));
        }
    }
}
