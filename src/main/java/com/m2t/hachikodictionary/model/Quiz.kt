package com.m2t.hachikodictionary.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name= "quizzes", schema = "public")
data class Quiz(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val difficulty: String

) {
    constructor(account: Account, correctAnswers: Int, incorrectAnswers: Int, difficulty: String) : this(
        null,
        account,
        correctAnswers,
        incorrectAnswers,
        difficulty
    ) {

    }
}