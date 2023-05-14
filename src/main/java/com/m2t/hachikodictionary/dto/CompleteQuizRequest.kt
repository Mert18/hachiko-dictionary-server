package com.m2t.hachikodictionary.dto

data class CompleteQuizRequest(
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val difficulty: String,
) {
}