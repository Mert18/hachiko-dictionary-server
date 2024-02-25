package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WordRepository extends MongoRepository<Word, String> {

    @Aggregation(pipeline = {
            "{ $match: { difficulty: ?0 } }",
            "{ $sample: { size: 1 } }"
    })
    Word findRandomWordByDifficulty(String difficulty);

    Word findWordByTitle(String title);

    Boolean existsByTitle(String title);

    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Word> searchByWord(String searchTerm);
}
