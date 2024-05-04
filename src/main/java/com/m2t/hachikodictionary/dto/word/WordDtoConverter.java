package com.m2t.hachikodictionary.dto.word;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WordDtoConverter {

    public Word dtoWordConverter(WordDto from) {
        return new Word(from.getId(), from.getTitle(), from.getKind(), from.getDifficulty(), from.getDescription(),
                from.getSynonyms(), from.getAntonyms(), from.getSentences(), from.getFileUrl(), from.getAudioFileCreatedAt());
    }
    public WordDto wordDtoConverter(Word from) {
        return new WordDto(from.getId(), from.getTitle(), from.getKind(), from.getDifficulty(),
                from.getDescriptions(), from.getSynonyms(),
                from.getAntonyms(), from.getSentences(), from.getFileUrl(), from.getAudioFileCreatedAt(), from.getEtymology());
    }
}
