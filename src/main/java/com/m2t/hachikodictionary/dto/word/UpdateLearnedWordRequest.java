package com.m2t.hachikodictionary.dto.word;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLearnedWordRequest {
    @NotBlank
    private String accountId;
    @NotBlank
    private String wordId;
    private Boolean result;
}