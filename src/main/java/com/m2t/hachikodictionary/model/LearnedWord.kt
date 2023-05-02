package com.m2t.hachikodictionary.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "learned_words")
data class LearnedWord(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // Generate UUID
    val id: String?,

    val account_id: String,
    val word_id: String,
    val level: Int,
){
    constructor() : this(null, "", "", 0)
}