package com.m2t.hachikodictionary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.dto.AccountDtoConverter;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.repository.AccountRepository;
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
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository, AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.converter = converter;
    }

    public Account findAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));
    }

    public Account findAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new AccountNotFoundException(username);
        }

        // Create the AccountDTO object
        return account;
    }

    public Account loadUserByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if(account == null) {
            throw new AccountNotFoundException("Account not found.");
        }

        return account;
    }

    public Account loadUserByEmail(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        if(account == null) {
            throw new AccountNotFoundException("Account not found.");
        }
        return account;
    }

    public AccountDto getAccountById(String id) {
        return converter.accountDtoConverter(findAccountById(id));
    }

    public AccountDto getAccountByUsername(String username) {
        return converter.accountDtoConverter(findAccountByUsername(username));
    }

    public void confirmAccount(String email) {
        Account account = accountRepository.findAccountByEmail(email);
        if(account == null) {
            throw new AccountNotFoundException("Account not found.");

        }

        Account updatedAccount = new Account(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getRole(),
                true
        );
        accountRepository.save(updatedAccount);
    }

    public Response isAccountConfirmed(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        jsonObject.put("confirmed", account.getConfirmed());
        jsonObject.put("email", account.getEmail());
        jsonObject.put("id", account.getId());

        return new Response(true, "Check is completed.", jsonObject, false);
    }

    // Beans for authentication process.
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
