package com.m2t.hachikodictionary.dto.account

data class ConfirmEmailRequest(
    val email: String,
    val token: String
) {
}