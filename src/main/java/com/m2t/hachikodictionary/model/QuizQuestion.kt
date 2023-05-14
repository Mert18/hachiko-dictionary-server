package com.m2t.hachikodictionary.model

data class QuizQuestion(
    val description: String,
    val answer: String,
    val choices: List<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QuizQuestion) return false

        if (description != other.description) return false
        if (answer != other.answer) return false
        return choices == other.choices
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + answer.hashCode()
        result = 31 * result + choices.hashCode()
        return result
    }
}