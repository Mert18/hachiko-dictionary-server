package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.AuthenticationResponse;
import com.m2t.hachikodictionary.dto.LoginRequest;
import com.m2t.hachikodictionary.dto.RegistrationRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.*;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Role;
import com.m2t.hachikodictionary.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(AccountRepository accountRepository, AccountService accountService, JWTService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Response register(RegistrationRequest registrationRequest) {
        if(accountRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists.");
        }
        if(accountRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        try {
            // Create new account model
            Account account = new Account(
                    registrationRequest.getUsername(),
                    passwordEncoder.encode(registrationRequest.getPassword()),
                    registrationRequest.getEmail(),
                    Role.USER
            );

            // Save it to the database
            accountRepository.save(account);

            // Generate the response with the JWT tokens
            AuthenticationResponse authResponse = jwtService.generateToken(account);
            Response response = new Response(true, "Registration successful.", authResponse);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    public Response login(LoginRequest loginRequest) throws Exception {
        try {
            Account user = accountService.loadUserByUsername(loginRequest.getUsername());
            if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("Invalid credentials.");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            Account account = accountRepository.findAccountByUsername(loginRequest.getUsername());
            AuthenticationResponse authResponse = jwtService.generateToken(account);
            Response response = new Response(true, "Login successful.", authResponse);
            return response;
        } catch (AccountNotFoundException e) {
            throw new AccountNotFoundException("Account not found.");
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException("Invalid credentials.");
        } catch (Exception e) {
            throw new Exception("Login failed: " + e.getMessage());
        }


    }

    public Response refreshToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            Account account = accountService.loadUserByUsername(username);
            if(!jwtService.isTokenValid(token, account)) {
                throw new InvalidTokenException("Token is invalid.");
            }
            AuthenticationResponse authResponse = jwtService.generateToken(account);
            Response response = new Response(true, "Token refreshed.", authResponse);
            return response;
        }
        catch (InvalidTokenException e) {
            throw new InvalidTokenException("Token is invalid.");
        }
        catch (Exception e) {
            throw new RuntimeException("Token refresh failed: " + e.getMessage());
        }

    }
}
