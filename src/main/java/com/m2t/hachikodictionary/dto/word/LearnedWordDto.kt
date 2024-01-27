package com.m2t.hachikodictionary.dto.word

import com.m2t.hachikodictionary.model.Account
import com.m2t.hachikodictionary.model.Word

data class LearnedWordDto(
    val account: Account,
    val word: Word,
    val level: Int,
) {
}