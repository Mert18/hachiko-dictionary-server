package com.m2t.hachikodictionary.dto

import com.m2t.hachikodictionary.model.Account

data class QuizDto(
    val account: Account,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val difficulty: String
) {
}