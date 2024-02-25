package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizRepository extends MongoRepository<Quiz, String> {

    List<Quiz> findAllByAccountId(String accountId);

}
