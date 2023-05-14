package com.m2t.hachikodictionary.dto

import com.m2t.hachikodictionary.model.Account

data class QuizResponse(
    val accountId: String,
    val gameCount: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val correctRate: Float,
) {

    constructor(accountId: String, gameCount: Int, correctCount: Int, incorrectCount: Int) : this(
        accountId,
        gameCount,
        correctCount,
        incorrectCount,
        if (gameCount == 0) 0f else correctCount.toFloat() / (correctCount + incorrectCount).toFloat())

}