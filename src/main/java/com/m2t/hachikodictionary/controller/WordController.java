package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.WordRequest;
import com.m2t.hachikodictionary.dto.WordResponse;
import com.m2t.hachikodictionary.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/word")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWord(@RequestBody WordRequest wordRequest) {
        wordService.createWord(wordRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WordResponse> getAllWords() {
        return wordService.getAllWords();
    }

}
