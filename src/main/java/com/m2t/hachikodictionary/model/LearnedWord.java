package com.m2t.hachikodictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("learned_words")
public class LearnedWord {

    @Id
    private Account account;

    private Word word;

    private int level;
}