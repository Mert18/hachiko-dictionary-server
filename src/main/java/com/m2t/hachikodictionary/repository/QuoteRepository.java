package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Quote;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteRepository extends MongoRepository<Quote, String> {

    @Aggregation(pipeline = {
            "{ $match: { difficulty: ?0 } }", // Filter by difficulty
            "{ $sample: { size: 1 } }" // Randomly select one document
    })
    Quote getRandomQuote(String difficulty);
}
