package com.m2t.hachikodictionary.dto.word;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWordRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String kind;
    private String difficulty;
    @NotBlank
    private Set<String> description;
    private Set<String> synonyms;
    private Set<String> antonyms;
    private Set<String> sentences;
    private String fileUrl;
}