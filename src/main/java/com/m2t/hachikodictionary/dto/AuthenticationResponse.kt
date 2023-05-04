package com.m2t.hachikodictionary.dto

import java.time.LocalDateTime
import java.util.Date

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val accessExpiresAt: LocalDateTime,
    val refreshExpiresAt: LocalDateTime,
    val isUser: Boolean,
    val isAdmin: Boolean,
) {
}