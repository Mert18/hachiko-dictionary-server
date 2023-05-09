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
    var id: String?,
    var title: String,
    var kind: String,

    @ElementCollection
    var descriptions: Set<String>,
    @ElementCollection
    var synonyms: Set<String>,
    @ElementCollection
    var antonyms: Set<String>,
    @ElementCollection
    var sentences: Set<String>,
) {
    constructor(
        title: String,
        kind: String,
        descriptions: MutableSet<String>,
        synonyms: MutableSet<String>,
        antonyms: MutableSet<String>,
        sentences: MutableSet<String>
    ) : this(
        null,
        title,
        kind,
        descriptions,
        synonyms,
        antonyms,
        sentences
    )
}