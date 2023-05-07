package com.m2t.hachikodictionary.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "confirmations")
data class Confirmation(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // Generate UUID
    val id: String,

    @Column(unique = true)
    val email: String,
    val token: String,
) {
    constructor(email: String, token: String) : this(
        "",
        email,
        token
    )
}