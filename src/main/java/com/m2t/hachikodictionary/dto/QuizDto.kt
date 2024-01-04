package com.m2t.hachikodictionary.dto

import com.m2t.hachikodictionary.model.Account

data class QuizDto(
    val id: String?,
    val account: Account,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val notAnswered: Int = 0,
    val difficulty: String
) {
}