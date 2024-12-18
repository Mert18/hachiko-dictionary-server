package com.m2t.hachikodictionary.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordOfTheDay {
    private Integer wordOfTheDayId;
    private String wordOfTheDayTitle;
    private String wordOfTheDayDefinition;
    private String wordOfTheDayType;
}



