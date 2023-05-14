package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, String> {

    List<Quiz> findAllByAccountId(String accountId);

}
