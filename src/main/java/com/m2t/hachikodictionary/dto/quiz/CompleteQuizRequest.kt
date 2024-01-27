package com.m2t.hachikodictionary.dto.quiz

data class CompleteQuizRequest(
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val notAnswered: Int,
    val difficulty: String,
) {
}