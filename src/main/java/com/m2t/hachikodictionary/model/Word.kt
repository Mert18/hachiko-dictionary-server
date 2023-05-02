package com.m2t.hachikodictionary.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "words")
data class Word(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // Generate UUID
    val id: String?,
    val title: String,
    val kind: String,
    val description: String,
    val synonyms: Set<String>,
    val antonyms: Set<String>,
    val sentences: Set<String>,
) {
    constructor() : this(null, "", "", "", setOf(), setOf(), setOf())
}