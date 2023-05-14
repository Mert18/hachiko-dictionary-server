package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WordPagingRepository extends PagingAndSortingRepository<Word, String> {

    Page<Word> findAll(Pageable pageable);
}
