package com.m2t.hachikodictionary.dto

import jakarta.validation.constraints.NotBlank

data class CreateWordRequest(
    @field:NotBlank(message = "Title cannot be blank.")
    val title: String,
    @field:NotBlank(message = "Kind cannot be blank.")
    val kind: String,
    val difficulty: String,
    @field:NotBlank(message = "Description cannot be blank.")
    val descriptions: Set<String>,
    val synonyms: Set<String>,
    val antonyms: Set<String>,
    val sentences: Set<String>,
) {

}