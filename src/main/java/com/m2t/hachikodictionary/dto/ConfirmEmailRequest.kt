package com.m2t.hachikodictionary.dto

data class ConfirmEmailRequest(
    val email: String,
    val token: String
) {
}