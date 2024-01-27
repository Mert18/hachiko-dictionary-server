package com.m2t.hachikodictionary.dto.account

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "Refresh token is required.")
    val refreshToken: String
) {
}