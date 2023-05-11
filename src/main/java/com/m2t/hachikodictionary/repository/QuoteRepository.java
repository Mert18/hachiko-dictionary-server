package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuoteRepository extends JpaRepository<Quote, String> {

    @Query(value = "SELECT u FROM Quote u WHERE u.difficulty= :difficulty ORDER BY RANDOM() LIMIT 1")
    Quote getRandomQuote(String difficulty);
}
