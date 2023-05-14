package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.LearnedWord;
import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnedWordRepository extends JpaRepository<LearnedWord, String> {

    LearnedWord findByAccountAndWord(Account account, Word word);
}
