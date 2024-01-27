package com.m2t.hachikodictionary.dto.word;

import com.m2t.hachikodictionary.model.LearnedWord;
import org.springframework.stereotype.Component;

@Component
public class LearnedWordDtoConverter {

        public LearnedWordDto learnedWordDtoConverter(LearnedWord from) {
            return new LearnedWordDto(from.getAccount(), from.getWord(), from.getLevel());
        }
        public LearnedWord dtoLearnedWordConverter(LearnedWordDto from) {
            return new LearnedWord(from.getAccount(), from.getWord(), from.getLevel());
        }
}
