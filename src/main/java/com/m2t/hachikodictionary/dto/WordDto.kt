package com.m2t.hachikodictionary.dto

data class WordDto(
    val id: String?,
    val title: String,
    val kind: String,
    val difficulty: String,
    val descriptions: Set<String>,
    val synonyms: Set<String>,
    val antonyms: Set<String>,
    val sentences: Set<String>,
) {
}

