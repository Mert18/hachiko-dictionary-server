package com.m2t.hachikodictionary.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator


@Entity
@Table(name = "quotes")
data class Quote(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // Generate UUID
    val id: String,

    @Column(unique = true)
    val quote: String,
    val author: String,
    val difficulty: String
) {
    constructor(quote: String, author: String, difficulty: String) : this(
        id = "",
        quote = quote,
        author = author,
        difficulty = difficulty
    ) {

    }
}