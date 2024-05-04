package com.m2t.hachikodictionary.dto.word;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    private String id;
    private String title;
    private String kind;
    private String difficulty;
    private Set<String> description;
    private Set<String> synonyms;
    private Set<String> antonyms;
    private Set<String> sentences;
    private String fileUrl;
    private LocalDateTime audioFileCreatedAt;
    private String etymology;
}