package com.m2t.hachikodictionary.dto.word

import jakarta.validation.constraints.NotBlank
import org.springframework.lang.NonNull

data class UpdateLearnedWordRequest(
    @field:NotBlank(message = "accountId must not be blank")
    val accountId: String,
    @field:NotBlank(message = "wordId must not be blank")
    val wordId: String,
    val result: Boolean
) {
}