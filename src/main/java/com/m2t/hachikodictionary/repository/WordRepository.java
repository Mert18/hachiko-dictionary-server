package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepository extends MongoRepository<Word, String> {
    
}
