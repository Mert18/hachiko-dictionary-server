package com.m2t.hachikodictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("quizzes")
public class Quiz {

    @Id
    private String id;

    private Account account;

    private int correctAnswers;
    private int incorrectAnswers;
    private int notAnswered;
    private String difficulty;
}