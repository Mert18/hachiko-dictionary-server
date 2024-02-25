package com.m2t.hachikodictionary.dto.quiz;

import com.m2t.hachikodictionary.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private String id;
    private Account account;
    private int correctAnswers;
    private int incorrectAnswers;
    private int notAnswered = 0;
    private String difficulty;
}