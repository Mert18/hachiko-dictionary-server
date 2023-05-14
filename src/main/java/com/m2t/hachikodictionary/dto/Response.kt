package com.m2t.hachikodictionary.dto

import java.time.LocalDateTime

data class Response(
    val timestamp: LocalDateTime?,
    val success: Boolean?,
    val message: String?,
    val data: Any? = null
) {
    constructor(success: Boolean, message: String) : this(
        timestamp = LocalDateTime.now(),
        success = success,
        message = message,
    )

    constructor(success: Boolean, message: String, data: Any) : this(
        timestamp = LocalDateTime.now(),
        success = success,
        message = message,
        data = data
    )
}