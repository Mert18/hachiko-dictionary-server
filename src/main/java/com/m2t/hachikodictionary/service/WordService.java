package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.client.WordnikClient;
import com.m2t.hachikodictionary.dto.client.WordAudio;
import com.m2t.hachikodictionary.dto.word.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.dto.word.WordDto;
import com.m2t.hachikodictionary.dto.word.WordDtoConverter;
import com.m2t.hachikodictionary.exception.WordAlreadyExistsException;
import com.m2t.hachikodictionary.exception.WordNotFoundException;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.WordPagingRepository;
import com.m2t.hachikodictionary.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WordService {
    private final WordRepository wordRepository;
    private final WordPagingRepository wordPagingRepository;
    private final WordDtoConverter wordDtoConverter;
    private final WordnikClient wordnikClient;

    private static final Logger logger = LoggerFactory.getLogger(WordService.class);

    public WordService(WordRepository wordRepository, WordDtoConverter wordDtoConverter, WordPagingRepository wordPagingRepository, WordnikClient wordnikClient) {
        this.wordRepository = wordRepository;
        this.wordDtoConverter = wordDtoConverter;
        this.wordPagingRepository = wordPagingRepository;
        this.wordnikClient = wordnikClient;
    }

    public Response getWord(String id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        if(word.getFileUrl() == null || (word.getAudioFileCreatedAt() != null && word.getAudioFileCreatedAt().isBefore(LocalDateTime.now().minusMinutes(10)))) {
            WordAudio wordAudio = wordnikClient.getWordAudio(word.getTitle());
            if(wordAudio != null) {
                word.setFileUrl(wordAudio.getFileUrl());
                word.setAudioFileCreatedAt(LocalDateTime.now());
            }else {
                word.setFileUrl("N/A");
            }
            wordRepository.save(word);
        }
        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);
        return new Response(true, "Word retrieval successful.", wordDto, false);
    }

    public Word findWordById(String id) {
        return wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
    }

    public Response getWordByTitle(String title) {
        Word word = wordRepository.findWordByTitle(title);
        if(word == null) {
            logger.error("Word with title {} not found.", title);
            throw new WordNotFoundException("Word not found.");
        }

        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

        return new Response(true, "Word retrieval successful.", wordDto, false);
    }

    public Response getAllWords(Pageable pageable) {
        Page<Word> words = wordPagingRepository.findAll(pageable);

        return new Response(true, "Word retrieval successful.", words, false);
    }

    public Response getRandomWord() {
        Word word = wordRepository.findRandomWord();
        if(word == null) {
            logger.error("No word found.");
            throw new WordNotFoundException("No word found.");
        }
        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

        return new Response(true, "Random word retrieval successful.", wordDto, false);
    }

    public Response createWord(CreateWordRequest createWordRequest) {
        if(wordRepository.existsByTitle(createWordRequest.getTitle())) {
            throw new WordAlreadyExistsException("Word already exists.");
        }
        Word word = new Word(
                createWordRequest.getTitle(),
                createWordRequest.getKind(),
                createWordRequest.getDifficulty(),
                createWordRequest.getDescription(),
                createWordRequest.getSynonyms(),
                createWordRequest.getAntonyms(),
                createWordRequest.getSentences(),
                null,
                null
        );

        wordRepository.save(word);
        logger.info("Word with title {} created.", word.getTitle());
        return new Response(true, "Word creation successful.", word);
    }

    public Response updateWord(String id, CreateWordRequest createWordRequest) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        word.setTitle(createWordRequest.getTitle());
        word.setKind(createWordRequest.getKind());
        word.setDifficulty(createWordRequest.getDifficulty());
        word.setDescriptions(createWordRequest.getDescription());
        word.setSynonyms(createWordRequest.getSynonyms());
        word.setAntonyms(createWordRequest.getAntonyms());
        word.setSentences(createWordRequest.getSentences());

        wordRepository.save(word);
        logger.info("Word with title {} updated.", word.getTitle());

        WordDto wordDto = wordDtoConverter.wordDtoConverter(word);
        return new Response(true, "Word update successful.", wordDto);
    }

    public Response deleteWord(String id) {
        Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
        wordRepository.delete(word);
        logger.info("Word with title {} deleted.", word.getTitle());
        return new Response(true, "Word deletion successful.", null);
    }
}
