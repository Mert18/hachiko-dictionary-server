package com.m2t.hachikodictionary.dto.quote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CreateQuoteRequest {
    private String quote;
    private String author;
    private String difficulty;

    public CreateQuoteRequest() {

    }

    public CreateQuoteRequest(String quote, String author, String difficulty) {
        this.quote = quote;
        this.author = author;
        this.difficulty = difficulty;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}