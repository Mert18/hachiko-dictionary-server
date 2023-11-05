package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.LoginRequest;
import com.m2t.hachikodictionary.dto.RefreshRequest;
import com.m2t.hachikodictionary.dto.RegistrationRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.*;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.register(registrationRequest));
        } catch (PasswordsDoNotMatchException | UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Registration failed."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            return ResponseEntity
                    .ok(authenticationService.login(loginRequest));
        } catch (AccountNotFoundException | InvalidCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Login failed."));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Response> refresh(@RequestBody @Valid RefreshRequest refreshRequest) {
        try {
            return ResponseEntity.ok(authenticationService.refreshToken(refreshRequest.getRefreshToken()));
        } catch (InvalidTokenException e) {
                return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Refresh token failed."));
        }
    }

}
