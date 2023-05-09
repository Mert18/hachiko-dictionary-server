package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WordPagingRepository extends PagingAndSortingRepository<Word, String> {

}
