package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.CreateQuoteRequest;
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

    public Response getRandomQuote(String difficulty) {
        try {
            Quote quote = quoteRepository.getRandomQuote(difficulty);
            if (quote == null) {
                throw new QuoteNotFoundException("Quote not found.");
            }
            logger.info("Quote returned.");
            return new Response(true, "Quote retrieved successfully.", quote);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Response(false, "Quote retrieval failed.");
        }
    }

    public Response createQuote(CreateQuoteRequest quote) {
        try {
            Quote newQuote = new Quote(quote.getQuote(), quote.getAuthor(), quote.getDifficulty());
            quoteRepository.save(newQuote);
            logger.info("Quote created.");
            return new Response(true, "Quote created successfully.", newQuote);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Response(false, "Quote creation failed.");
        }
    }

    public Response deleteQuote(String id) {
        try {
            quoteRepository.deleteById(id);
            logger.info("Quote deleted.");
            return new Response(true, "Quote deleted successfully.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Response(false, "Quote deletion failed.");
        }
    }


}
