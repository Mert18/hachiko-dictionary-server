package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.dto.CreateAccountRequest;
import com.m2t.hachikodictionary.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        accountService.createAccount(createAccountRequest);
        return ResponseEntity.ok().build();
    }

}
