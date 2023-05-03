package com.m2t.hachikodictionary.dto

import com.m2t.hachikodictionary.model.Role

data class CreateAccountRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: Role = Role.USER
) {

}