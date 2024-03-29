package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.account.AccountDto;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final JWTService jwtService;

    public AccountController(AccountService accountService, JWTService jwtService) {
        this.accountService = accountService;
        this.jwtService = jwtService;
    }

    @GetMapping("/me")
    public ResponseEntity<AccountDto> getAccount(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String accountId = jwtService.extractAccountId(jwtToken);
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
    @GetMapping("/id/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<AccountDto> getAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }

    @GetMapping("/isconfirmed/{id}")
    public ResponseEntity<Response> isConfirmed(@PathVariable String id) {
        return ResponseEntity.ok(accountService.isAccountConfirmed(id));
    }
}
