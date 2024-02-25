package com.m2t.hachikodictionary.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordAudio {
    private Integer id;
    private String word;
    private Double duration;
    private String fileUrl;
}