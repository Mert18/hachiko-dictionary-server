package com.m2t.hachikodictionary.dto

data class RegistrationRequest(
    val username: String,
    val password: String,
    val email: String
) {
}