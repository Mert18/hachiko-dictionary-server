package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.WordAlreadyExistsException;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WordService {
    private final WordRepository wordRepository;

    private static final Logger logger = LoggerFactory.getLogger(WordService.class);


    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Response createWord(CreateWordRequest createWordRequest) {
        if(wordRepository.existsByTitle(createWordRequest.getTitle())) {
            throw new WordAlreadyExistsException("Word already exists.");
        }
        try {
            // Create the word model
            Word word = new Word(
                    createWordRequest.getTitle(),
                    createWordRequest.getKind(),
                    createWordRequest.getDescription(),
                    createWordRequest.getSynonyms(),
                    createWordRequest.getAntonyms(),
                    createWordRequest.getSentences()
            );

            // Save it to the database
            wordRepository.save(word);

            Response response = new Response(true, "Word creation successful.", word);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Word creation failed: " + e.getMessage());
        }
    }

}
