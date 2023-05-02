package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.dto.CreateAccountRequest;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(CreateAccountRequest createAccountRequest) {
        Account account = new Account(createAccountRequest.getUsername(), createAccountRequest.getPassword()
        , createAccountRequest.getEmail());
        accountRepository.save(account);
    }

    public AccountDto getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .map(account -> new AccountDto(account.getId(), account.getUsername(), account.getEmail()))
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }



}
