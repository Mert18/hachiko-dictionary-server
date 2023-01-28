package com.m2t.hachikodictionary.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("words")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Word {
    @Id
    private String id;

    private String title;
    private String kind;
    private List<String> description;
    private List<String> synonyms;
    private List<String> antonyms;
    private List<String> sentences;


}
