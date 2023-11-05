package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.CreateQuoteRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.QuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quote")
public class QuoteController {
    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/one/{difficulty}")
    public ResponseEntity<Response> getOneQuoteByDifficulty(@PathVariable String difficulty) {
        try {
            return ResponseEntity.ok(quoteService.getRandomQuote(difficulty));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Quote retrieval failed."));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createQuote(@RequestBody CreateQuoteRequest quote) {
        try {
            return ResponseEntity.ok(quoteService.createQuote(quote));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Quote creation failed."));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteQuote(@PathVariable String id) {
        try {
            return ResponseEntity.ok(quoteService.deleteQuote(id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(new Response(false, "Quote deletion failed."));
        }
    }

}