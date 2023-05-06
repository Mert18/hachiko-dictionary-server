package com.m2t.hachikodictionary.model

import jakarta.persistence.ElementCollection
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
    @ElementCollection
    val synonyms: Set<String>,
    @ElementCollection
    val antonyms: Set<String>,
    @ElementCollection
    val sentences: Set<String>,
) {
}