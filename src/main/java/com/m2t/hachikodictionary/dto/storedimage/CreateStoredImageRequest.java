package com.m2t.hachikodictionary.dto.storedimage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoredImageRequest {
    private String url;
    private LocalDate date;
}
