package com.m2t.hachikodictionary.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmEmailRequest {
    private String email;
    private String token;
}