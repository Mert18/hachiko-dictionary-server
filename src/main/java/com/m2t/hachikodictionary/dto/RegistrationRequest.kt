package com.m2t.hachikodictionary.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegistrationRequest(

    @field:NotBlank(message = "Username cannot be blank.")
    @field:Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters.")
    val username: String,

    @field:NotBlank(message = "Password cannot be blank.")
    @field:Size(min = 6, message = "Password must be at least 6 characters.")
    val password: String,
    val confirmPassword: String,

    @field:Email(message = "Email must be valid.")
    val email: String,
) {
}