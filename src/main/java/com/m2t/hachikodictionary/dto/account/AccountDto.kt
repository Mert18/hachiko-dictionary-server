package com.m2t.hachikodictionary.dto.account

import com.m2t.hachikodictionary.model.Role


data class AccountDto(
    val id: String?,
    val username: String,
    val password: String,
    val email: String,
    val role: Role? = Role.USER

) {

}