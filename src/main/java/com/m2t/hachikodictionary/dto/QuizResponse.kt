package com.m2t.hachikodictionary.dto

import com.m2t.hachikodictionary.util.Constants


data class QuizResponse(
    val accountId: String,
    val gameCount: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val notAnsweredCount: Int,
    val totalQuestions: Int,
) {
    var correctRate: Double = 0.0

    init {
        correctRate = if (totalQuestions > 0) {
            ((correctCount.toDouble() - Constants.QUIZ_UNANSWERED_PENALTY * incorrectCount.toDouble()
                    - Constants.QUIZ_UNANSWERED_PENALTY * notAnsweredCount.toDouble()) / totalQuestions.toDouble())
                    .coerceIn(0.0, 1.0)
        } else {
            0.0
        }
    }
}