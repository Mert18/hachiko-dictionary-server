package com.m2t.hachikodictionary.dto;

import com.m2t.hachikodictionary.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {

    public AccountDto accountDtoConverter(Account from) {
        return new AccountDto(from.getId(), from.getUsername(), from.getPassword(), from.getEmail(), from.getRole());
    }
    public Account dtoAccountConverter(AccountDto from) {
        return new Account(from.getId(), from.getUsername(), from.getEmail(), from.getRole());
    }
}
