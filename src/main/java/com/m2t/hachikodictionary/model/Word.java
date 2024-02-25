package com.m2t.hachikodictionary.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Document("words")
public class Word {

    @Id
    private String id;

    private String title;
    private String kind;
    private String difficulty = "medium";

    private Set<String> descriptions;

    private Set<String> synonyms;

    private Set<String> antonyms;

    private Set<String> sentences;

    private Set<LearnedWord> learnedWords;

    private String fileUrl;

    private LocalDateTime audioFileCreatedAt;

    public Word(String title, String kind, String difficulty, Set<String> descriptions, Set<String> synonyms, Set<String> antonyms, Set<String> sentences, Set<LearnedWord> learnedWords, String fileUrl) {
        this.title = title;
        this.kind = kind;
        this.difficulty = difficulty;
        this.descriptions = descriptions;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.sentences = sentences;
        this.learnedWords = learnedWords;
        this.fileUrl = fileUrl;
    }

    public Word(String id, String title, String kind, String difficulty, Set<String> descriptions, Set<String> synonyms, Set<String> antonyms, Set<String> sentences, Set<LearnedWord> learnedWords, String fileUrl, LocalDateTime audioFileCreatedAt) {
        this.id = id;
        this.title = title;
        this.kind = kind;
        this.difficulty = difficulty;
        this.descriptions = descriptions;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.sentences = sentences;
        this.learnedWords = learnedWords;
        this.fileUrl = fileUrl;
        this.audioFileCreatedAt = audioFileCreatedAt;
    }

    public Word(String id, String title, String kind, String difficulty, Set<String> descriptions, Set<String> synonyms, Set<String> antonyms, Set<String> sentences, String fileUrl, LocalDateTime audioFileCreatedAt) {
        this.id = id;
        this.title = title;
        this.kind = kind;
        this.difficulty = difficulty;
        this.descriptions = descriptions;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.sentences = sentences;
        this.fileUrl = fileUrl;
        this.audioFileCreatedAt = audioFileCreatedAt;
    }
}