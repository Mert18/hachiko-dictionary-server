package com.m2t.hachikodictionary.model

data class NewQuiz(
    val questions: List<QuizQuestion>,
    val difficulty: String
) {
}