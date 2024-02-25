package com.m2t.hachikodictionary.dto.word;

import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearnedWordDto {
    private Account account;
    private Word word;
    private int level;
}