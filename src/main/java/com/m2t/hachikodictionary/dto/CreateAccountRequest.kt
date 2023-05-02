package com.m2t.hachikodictionary.dto

data class CreateAccountRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String = "USER"
) {

}