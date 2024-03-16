package com.m2t.hachikodictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("storedImages")
public class StoredImage {
    @Id
    private String id;

    private String url;
    private String description;
    private LocalDate date;
    private LocalDateTime uploadedAt;
}
