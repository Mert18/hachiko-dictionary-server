package com.m2t.hachikodictionary.dto;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.stereotype.Component;

@Component
public class WordDtoConverter {

    public Word dtoWordConverter(WordDto from) {
        return new Word(from.getId(), from.getTitle(), from.getKind(), from.getDescription(), from.getSynonyms(), from.getAntonyms(), from.getSentences());
    }
    public WordDto wordDtoConverter(Word from) {
        return new WordDto(from.getId(), from.getTitle(), from.getKind(), from.getDescription(), from.getSynonyms(), from.getAntonyms(), from.getSentences());
    }
}
