package com.m2t.hachikodictionary.dto.quote

data class CreateQuoteRequest(
    val quote: String,
    val author: String,
    val difficulty: String
) {
}