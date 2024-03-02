package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.quote.CreateQuoteRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.QuoteNotFoundException;
import com.m2t.hachikodictionary.model.Quote;
import com.m2t.hachikodictionary.repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public Response getRandomQuote() {
        Quote quote = quoteRepository.getRandomQuote();
        if (quote == null) {
            throw new QuoteNotFoundException("Quote not found.");
        }
        return new Response(true, "Quote retrieved successfully.", quote, false);
    }

    public Response createQuote(CreateQuoteRequest quote) {
        Quote newQuote = new Quote(quote.getQuote(), quote.getAuthor());
        quoteRepository.save(newQuote);
        logger.info("Quote created: {}", quote.getQuote());
        return new Response(true, "Quote created successfully.", newQuote);
    }

    public Response deleteQuote(String id) {
        quoteRepository.deleteById(id);
        logger.info("Quote deleted: {}", id);
        return new Response(true, "Quote deleted successfully.");
    }
}
