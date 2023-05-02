package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.dto.CreateAccountRequest;
import com.m2t.hachikodictionary.dto.CustomUserDetails;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(CreateAccountRequest createAccountRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Account account = new Account(createAccountRequest.getUsername(), bCryptPasswordEncoder.encode(createAccountRequest.getPassword())
        , createAccountRequest.getEmail());
        accountRepository.save(account);
    }
    public AccountDto getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .map(account -> new AccountDto(account.getId(), account.getUsername(), account.getEmail(), account.getRole()))
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public AccountDto getAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account == null) {
            throw new AccountNotFoundException(username);
        }
        return new AccountDto(account.getId(), account.getUsername(), account.getEmail(), account.getRole());
    }

    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        return new CustomUserDetails(account.getUsername(), account.getPassword(), account.getRole());
    }



}
