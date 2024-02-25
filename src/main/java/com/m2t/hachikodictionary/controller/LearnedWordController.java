package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.dto.word.UpdateLearnedWordRequest;
import com.m2t.hachikodictionary.service.LearnedWordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/learned-word")
@Validated
public class LearnedWordController {

    private final LearnedWordService learnedWordService;

    public LearnedWordController(LearnedWordService learnedWordService) {
        this.learnedWordService = learnedWordService;
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateLearnedWord(@RequestBody @Valid UpdateLearnedWordRequest updateLearnedWordRequest) {
        return ResponseEntity.ok(learnedWordService.updateLearnedWord(updateLearnedWordRequest));
    }
}
