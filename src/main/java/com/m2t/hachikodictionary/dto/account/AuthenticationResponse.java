package com.m2t.hachikodictionary.dto.account;

import com.m2t.hachikodictionary.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private LocalDateTime accessExpiresAt;
    private LocalDateTime refreshExpiresAt;
    private Boolean isUser;
    private Boolean isAdmin;
    private String username;
    private String email;
    private Role role; // Assuming Role is an enum or a class you've defined
}