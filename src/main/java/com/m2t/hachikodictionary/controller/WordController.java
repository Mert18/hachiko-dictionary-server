package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.WordAlreadyExistsException;
import com.m2t.hachikodictionary.exception.WordNotFoundException;
import com.m2t.hachikodictionary.service.WordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/word")
public class WordController {
    private final WordService wordService;
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllWords() {
        try {
            return ResponseEntity
                    .ok(wordService.getAllWords());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Word retrieval failed."));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getWord(@PathVariable String id) {
        try {
            return ResponseEntity
                    .ok(wordService.getWord(id));
        } catch(WordNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Word retrieval failed."));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createWord(@RequestBody @Valid CreateWordRequest createWordRequest) {
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

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateWord(@PathVariable String id, @RequestBody @Valid CreateWordRequest createWordRequest) {
        try {
            return ResponseEntity
                    .ok(wordService.updateWord(id, createWordRequest));
        } catch (WordNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Word update failed."));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteWord(@PathVariable String id) {
        try {
            return ResponseEntity
                    .ok(wordService.deleteWord(id));
        } catch (WordNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Word deletion failed."));
        }
    }
}
