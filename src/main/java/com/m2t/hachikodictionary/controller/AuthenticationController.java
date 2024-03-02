package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.account.LoginRequest;
import com.m2t.hachikodictionary.dto.account.RefreshRequest;
import com.m2t.hachikodictionary.dto.account.RegistrationRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.AuthenticationService;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegistrationRequest registrationRequest) throws PostmarkException, IOException {
        return ResponseEntity.ok(authenticationService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refresh(@RequestBody @Valid RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshRequest.getRefreshToken()));
    }

}
