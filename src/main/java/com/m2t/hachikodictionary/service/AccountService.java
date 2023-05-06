package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .map(account -> new AccountDto(account.getId(), account.getUsername(), account.getEmail(), account.getRole()))
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));
    }

    public AccountDto getAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new AccountNotFoundException(username);
        }

        // Create the AccountDTO object
        AccountDto accountDto = new AccountDto(account.getId(), account.getUsername(), account.getEmail(), account.getRole());
        return accountDto;
    }

    public Account loadUserByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if(account == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        return account;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(AccountService.this);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
