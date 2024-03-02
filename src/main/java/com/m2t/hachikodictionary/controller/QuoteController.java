package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.quote.CreateQuoteRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quote")
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/random")
    public ResponseEntity<Response> getRandomQuote() {
        return ResponseEntity.ok(quoteService.getRandomQuote());
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createQuote(@RequestBody CreateQuoteRequest quote) {
        return ResponseEntity.ok(quoteService.createQuote(quote));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteQuote(@PathVariable String id) {
        return ResponseEntity.ok(quoteService.deleteQuote(id));
    }

}