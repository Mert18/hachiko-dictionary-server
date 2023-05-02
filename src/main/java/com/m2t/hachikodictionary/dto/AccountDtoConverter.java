package com.m2t.hachikodictionary.dto;

import com.m2t.hachikodictionary.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {
    public AccountDto convert(Account from) {
        return new AccountDto(from.getId(), from.getUsername(), from.getEmail(), from.getRole());
    }
}
