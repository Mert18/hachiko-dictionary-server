package com.m2t.hachikodictionary.model;

import java.util.List;
import java.util.Objects;

public class QuizQuestion {

    private String description;
    private String answer;
    private List<String> choices;

    // Constructor
    public QuizQuestion(String description, String answer, List<String> choices) {
        this.description = description;
        this.answer = answer;
        this.choices = choices;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    // equals and hashCode using Java Objects utility
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestion that = (QuizQuestion) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(answer, that.answer) &&
                Objects.equals(choices, that.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, answer, choices);
    }
}