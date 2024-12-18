package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.word.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.WordService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/word")
@Validated
public class WordController {
    private final WordService wordService;
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllWords(Pageable pageable) {
        return ResponseEntity.ok(wordService.getAllWords(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getWord(@PathVariable String id) {
        return ResponseEntity.ok(wordService.getWord(id));
    }

    @GetMapping("/random")
    public ResponseEntity<Response> getRandomWord() {
        return ResponseEntity.ok(wordService.getRandomWord());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Response> getWordByTitle(@PathVariable String title) {
        return ResponseEntity.ok(wordService.getWordByTitle(title));
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createWord(@RequestBody CreateWordRequest createWordRequest) {
        return ResponseEntity.ok(wordService.createWord(createWordRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateWord(@PathVariable String id, @RequestBody CreateWordRequest createWordRequest) {
        return ResponseEntity.ok(wordService.updateWord(id, createWordRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteWord(@PathVariable String id) {
        return ResponseEntity.ok(wordService.deleteWord(id));
    }

    @GetMapping("/random-etymology")
    public ResponseEntity<Response> getRandomEtymology() {
        return ResponseEntity.ok(wordService.getRandomWordWithEtymology());
    }

    @GetMapping("/word-of-the-day")
    public ResponseEntity<Response> getWordOfTheDay() {
        return ResponseEntity.ok(wordService.getWordOfTheDay());
    }

    @GetMapping("/word-of-the-day-local")
    public ResponseEntity<Response> getWordOfTheDayLocal() {
        System.out.println(wordService.getWordOfTheDayLocal());
        return ResponseEntity.ok(wordService.getWordOfTheDayLocal());
    }
}
