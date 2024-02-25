package com.m2t.hachikodictionary.dto.quiz;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteQuizRequest {
    private int correctAnswers;
    private int incorrectAnswers;
    private int notAnswered;
    private String difficulty;
}