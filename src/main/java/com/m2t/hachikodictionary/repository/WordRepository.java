package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WordRepository extends MongoRepository<Word, String> {

    @Aggregation(pipeline = {
            "{ $sample: { size: 1 } }"
    })
    Word findRandomWord();

    Word findWordByTitle(String title);

    Boolean existsByTitle(String title);
}
