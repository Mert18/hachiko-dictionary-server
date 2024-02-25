package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.account.AuthenticationResponse;
import com.m2t.hachikodictionary.dto.account.LoginRequest;
import com.m2t.hachikodictionary.dto.account.RegistrationRequest;
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
    private final MailService mailService;
    private final ConfirmationService confirmationService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(AccountRepository accountRepository, AccountService accountService,
                                 JWTService jwtService, AuthenticationManager authenticationManager,
                                 PasswordEncoder passwordEncoder, MailService mailService,
                                 ConfirmationService confirmationService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.confirmationService = confirmationService;
    }

    public Response register(RegistrationRequest registrationRequest) {
        // Checks
        if(!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            throw new PasswordsDoNotMatchException("Passwords do not match.");
        }
        if(accountRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists.");
        }
        if(accountRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        Account account = new Account(
                registrationRequest.getUsername(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                registrationRequest.getEmail(),
                Role.USER
        );

        // Save to database
        accountRepository.save(account);
        logger.info("Account {} saved to database.", account.getUsername());

        // Generate token
        AuthenticationResponse authResponse = jwtService.generateToken(account);
        String token = confirmationService.create(registrationRequest.getEmail());

        // Send email
        mailService.sendConfirmationEmail(registrationRequest, token);
        logger.info("Confirmation mail sent to user {}", account.getUsername());

        return new Response(true, "Registration successful.", authResponse);
    }

    public Response login(LoginRequest loginRequest) {
        // Account existence check.
        Account account = accountService.loadUserByEmail(loginRequest.getEmail());
        if(account == null) {
            throw new AccountNotFoundException("Account not found with email " + loginRequest.getEmail());
        }
        // Password check.
        if(!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getUsername(),
                        loginRequest.getPassword()
                ));

        AuthenticationResponse authResponse = jwtService.generateToken(account);
        logger.info("{} login successful.", account.getUsername());

        return new Response(true, "Login successful.", authResponse);
    }

    public Response refreshToken(String token) {
        String username = jwtService.extractUsername(token);
        Account account = accountService.loadUserByUsername(username);
        if(!jwtService.isTokenValid(token, account)) {
            throw new InvalidTokenException("Token could not be validated.");
        }
        AuthenticationResponse authResponse = jwtService.generateToken(account);
        Response response = new Response(true, "Token refreshed.", authResponse, false);
        return response;
    }
}
