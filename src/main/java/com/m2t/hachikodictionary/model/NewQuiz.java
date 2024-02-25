package com.m2t.hachikodictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewQuiz {
    private List<QuizQuestion> questions;
    private String difficulty;
}