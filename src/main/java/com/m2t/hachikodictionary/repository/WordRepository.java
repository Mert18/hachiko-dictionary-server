package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WordRepository extends JpaRepository<Word, String> {

    @Query("SELECT u FROM Word u WHERE u.difficulty= :difficulty ORDER BY RANDOM() LIMIT 1")
    Word findRandomWordByDifficulty(String difficulty);

    Word findWordByTitle(String title);

    Boolean existsByTitle(String title);
}
