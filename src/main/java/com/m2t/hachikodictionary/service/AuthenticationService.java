package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.AuthenticationResponse;
import com.m2t.hachikodictionary.dto.LoginRequest;
import com.m2t.hachikodictionary.dto.RegistrationRequest;
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

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        Account account = new Account(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()), registrationRequest.getEmail(), Role.USER);
        accountRepository.save(account);
        return jwtService.generateToken(account);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        Account account = accountRepository.findAccountByUsername(loginRequest.getUsername());
        return jwtService.generateToken(account);
    }

    public AuthenticationResponse refreshToken(String token) {
        String username = jwtService.extractUsername(token);
        logger.info("Username: " + username);
        Account account = accountService.loadUserByUsername(username);
        logger.info("Account: " + account);
        if(!jwtService.isTokenValid(token, account)) {
            logger.error("Invalid token");
            return null;
        }
    logger.info("Token is valid");
        return jwtService.generateToken(account);
    }
}
