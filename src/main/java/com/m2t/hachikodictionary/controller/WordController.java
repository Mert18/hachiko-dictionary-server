package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.WordAlreadyExistsException;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/word")
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createWord(CreateWordRequest createWordRequest) {
        try {
            return ResponseEntity
                    .ok(wordService.createWord(createWordRequest));
        } catch (WordAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Word creation failed."));
        }
    }
}
