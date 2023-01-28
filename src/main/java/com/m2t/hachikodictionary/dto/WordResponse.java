package com.m2t.hachikodictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordResponse {
    private String id;
    private String title;
    private String kind;
    private List<String> description;
    private List<String> synonyms;
    private List<String> antonyms;
    private List<String> sentences;
}
