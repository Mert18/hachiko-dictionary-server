package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<AccountDto> getAccount(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        String token = header.substring(7);
        String accountId = jwtService.extractAccountId(token);
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
