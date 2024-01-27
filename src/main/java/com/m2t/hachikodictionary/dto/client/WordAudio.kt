package com.m2t.hachikodictionary.dto.client

import java.time.LocalDateTime

data class WordAudio(
        val id: Integer,
        val word: String,
        val duration: Double,
        val fileUrl: String
) {

}