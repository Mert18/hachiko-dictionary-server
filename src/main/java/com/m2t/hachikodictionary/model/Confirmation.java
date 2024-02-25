package com.m2t.hachikodictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("confirmations")
public class Confirmation {

    @Id
    private String id;

    private String email;
    private String token;

    public Confirmation(String email, String token) {
        this.email = email;
        this.token = token;
    }
}