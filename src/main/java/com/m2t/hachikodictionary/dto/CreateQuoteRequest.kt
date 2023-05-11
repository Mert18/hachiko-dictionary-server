package com.m2t.hachikodictionary.dto

data class CreateQuoteRequest(
    val quote: String,
    val author: String,
    val difficulty: String
) {
}