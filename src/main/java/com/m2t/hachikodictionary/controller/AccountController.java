package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/id/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<AccountDto> getAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }

}
