package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.WordRequest;
import com.m2t.hachikodictionary.dto.WordResponse;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordService {
    private final WordRepository wordRepository;

    public void createWord(WordRequest wordRequest) {
        Word word = Word.builder()
                .title(wordRequest.getTitle())
                .sentences(wordRequest.getSentences())
                .kind(wordRequest.getKind())
                .description(wordRequest.getDescription())
                .antonyms(wordRequest.getAntonyms())
                .synonyms(wordRequest.getSynonyms())
                .build();

        wordRepository.save(word);
        log.info("Word {} is saved.", word.getId());
    }

    public List<WordResponse> getAllWords(){
        List<Word> words = wordRepository.findAll();
        return words.stream().map(this::mapToWordResponse).toList();
    }

    private WordResponse mapToWordResponse(Word word) {
        return WordResponse.builder().title(word.getTitle())
                .sentences(word.getSentences())
                .kind(word.getKind())
                .description(word.getDescription())
                .antonyms(word.getAntonyms())
                .synonyms(word.getSynonyms())
                .build();
    }
}
