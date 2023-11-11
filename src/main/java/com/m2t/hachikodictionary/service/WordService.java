package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.dto.WordDto;
import com.m2t.hachikodictionary.dto.WordDtoConverter;
import com.m2t.hachikodictionary.exception.WordAlreadyExistsException;
import com.m2t.hachikodictionary.exception.WordNotFoundException;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.WordPagingRepository;
import com.m2t.hachikodictionary.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WordService {
    private final WordRepository wordRepository;
    private final WordPagingRepository wordPagingRepository;
    private final WordDtoConverter wordDtoConverter;

    private static final Logger logger = LoggerFactory.getLogger(WordService.class);


    public WordService(WordRepository wordRepository, WordDtoConverter wordDtoConverter, WordPagingRepository wordPagingRepository) {
        this.wordRepository = wordRepository;
        this.wordDtoConverter = wordDtoConverter;
        this.wordPagingRepository = wordPagingRepository;
    }

    public Response getWord(String id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);
        Response response = new Response(true, "Word retrieval successful.", wordDto, false);
        return response;
    }

    public Word findWordById(String id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        return word;
    }

    public Response getWordByTitle(String title) {
        Word word = wordRepository.findWordByTitle(title);
        if(word == null) {
            throw new WordNotFoundException("Word not found.");
        }

        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

        Response response = new Response(true, "Word retrieval successful.", wordDto, false);
        return response;
    }

    public Response getAllWords(Pageable pageable) {
        Iterable<Word> words = wordPagingRepository.findAll(pageable);
        if(words == null) {
            throw new WordNotFoundException("Word not found.");
        }

        Response response = new Response(true, "Word retrieval successful.", words, false);
        return response;
    }

    public Response getOneWordByDifficulty(String difficulty) {
        Word word = wordRepository.findRandomWordByDifficulty(difficulty);
        if(word == null) {
            throw new WordNotFoundException("Word not found.");
        }
        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

        Response response = new Response(true, "Random word retrieval successful.", wordDto, false);
        return response;
    }

    public Response createWord(CreateWordRequest createWordRequest) {
        if(wordRepository.existsByTitle(createWordRequest.getTitle())) {
            throw new WordAlreadyExistsException("Word already exists.");
        }
        Word word = new Word(
                createWordRequest.getTitle(),
                createWordRequest.getKind(),
                createWordRequest.getDescriptions(),
                createWordRequest.getSynonyms(),
                createWordRequest.getAntonyms(),
                createWordRequest.getSentences()
        );
        wordRepository.save(word);

        Response response = new Response(true, "Word creation successful.", word);
        return response;
    }

    public Response updateWord(String id, CreateWordRequest createWordRequest) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        word.setTitle(createWordRequest.getTitle());
        word.setKind(createWordRequest.getKind());
        word.setDescriptions(createWordRequest.getDescriptions());
        word.setSynonyms(createWordRequest.getSynonyms());
        word.setAntonyms(createWordRequest.getAntonyms());
        word.setSentences(createWordRequest.getSentences());

        wordRepository.save(word);
        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

        Response response = new Response(true, "Word update successful.", wordDto);
        return response;
    }

    public Response deleteWord(String id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        wordRepository.delete(word);
        Response response = new Response(true, "Word deletion successful.", null);
        return response;
    }
}
