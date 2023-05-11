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
        try {
            Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
            WordDto wordDto = wordDtoConverter.wordDtoConverter(word);
            Response response = new Response(true, "Word retrieval successful.", wordDto);
            return response;
        } catch(WordNotFoundException e) {
            throw new WordNotFoundException("Word not found.");
        } catch (Exception e) {
            throw new RuntimeException("Word retrieval failed: " + e.getMessage());
        }
    }

    public Response getWordByTitle(String title) {
        try {
            Word word = wordRepository.findWordByTitle(title);
            if(word == null) {
                throw new WordNotFoundException("Word not found.");
            }

            WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

            Response response = new Response(true, "Word retrieval successful.", wordDto);
            return response;
        } catch(WordNotFoundException e) {
            throw new WordNotFoundException("Word not found.");
        } catch (Exception e) {
            throw new RuntimeException("Word retrieval failed: " + e.getMessage());
        }
    }

    public Response getAllWords(Pageable pageable) {
        try {
            Iterable<Word> words = wordPagingRepository.findAll(pageable);
            if(words == null) {
                throw new WordNotFoundException("Word not found.");
            }

            Response response = new Response(true, "Word retrieval successful.", words);
            return response;
        } catch(WordNotFoundException e) {
            throw new WordNotFoundException("Word not found.");
        } catch (Exception e) {
            throw new RuntimeException("Word retrieval failed: " + e.getMessage());
        }
    }

    public Response getOneWordByDifficulty(String difficulty) {
        try {
            Word word = wordRepository.findRandomWordByDifficulty(difficulty);
            if(word == null) {
                throw new WordNotFoundException("Word not found.");
            }
            WordDto wordDto = wordDtoConverter.wordDtoConverter(word);

            Response response = new Response(true, "Random word retrieval successful.", wordDto);
            return response;
        } catch(WordNotFoundException e) {
            throw new WordNotFoundException("Word not found.");
        } catch (Exception e) {
            throw new RuntimeException("Word retrieval failed: " + e.getMessage());
        }
    }

    public Response createWord(CreateWordRequest createWordRequest) {
        if(wordRepository.existsByTitle(createWordRequest.getTitle())) {
            throw new WordAlreadyExistsException("Word already exists.");
        }
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Word creation failed: " + e.getMessage());
        }
    }

    public Response updateWord(String id, CreateWordRequest createWordRequest) {
        try {
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
        } catch (WordNotFoundException e) {
            throw new WordNotFoundException("Word not found.");
        } catch (Exception e) {
            throw new RuntimeException("Word update failed: " + e.getMessage());
        }
    }

    public Response deleteWord(String id) {
        try {
            Word word = wordRepository.findById(id).orElseThrow(() -> new WordNotFoundException("Word not found."));
            wordRepository.delete(word);
            Response response = new Response(true, "Word deletion successful.", null);
            return response;
        } catch (WordNotFoundException e) {
            throw new WordNotFoundException("Word not found.");
        } catch (Exception e) {
            throw new RuntimeException("Word deletion failed: " + e.getMessage());
        }

    }

}
