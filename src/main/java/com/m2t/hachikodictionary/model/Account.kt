package com.m2t.hachikodictionary.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // Generate UUID
    val id: String?,

    val username: String,
    val password: String,
    val email: String,
    val role: String = "USER",

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "account_id")
    val learnedWords: Set<LearnedWord>
){
    constructor() : this(null, "", "", "", "", setOf())
    constructor(username: String, password: String, email: String): this (
        null,
        username,
        password,
        email,
        "USER",
        setOf()
    )
}