package com.m2t.hachikodictionary.dto

data class AccountDto(
    val id: String?,
    val username: String,
    val email: String
) {
    constructor() : this(null, "", "")
}