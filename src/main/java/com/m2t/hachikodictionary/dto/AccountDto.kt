package com.m2t.hachikodictionary.dto

import com.m2t.hachikodictionary.model.Role


data class AccountDto(
    val id: String?,
    val username: String,
    val email: String,
    val role: Role? = Role.USER
) {

}