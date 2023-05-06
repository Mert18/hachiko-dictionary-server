package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.AccountDto;
import com.m2t.hachikodictionary.dto.AccountDtoConverter;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Role;
import com.m2t.hachikodictionary.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private AccountService service;
    private AccountRepository accountRepository;
    private AccountDtoConverter converter;

    @BeforeEach
    public void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        converter =  Mockito.mock(AccountDtoConverter.class);
        service = new AccountService(accountRepository, converter);
    }

    @Test
    public void testFindByAccountId_whenAccountExists_shouldReturnAccount() {
        Account account = new Account("1", "username", "password", "email", Role.USER);
        Mockito.when(accountRepository.findById("1")).thenReturn(Optional.of(account));

        Account result = service.findAccountById("1");
        assertEquals(result, account);
    }

    @Test
    public void testFindByAccountId_whenAccountDoesNotExist_shouldThrowAccountNotFoundException() {
        Mockito.when(accountRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,() -> service.findAccountById("1"));
    }

    @Test
    public void testGetByAccountId_whenAccountExists_shouldReturnAccount() {
        Account account = new Account("1", "username", "password", "email", Role.USER);
        AccountDto accountDto = new AccountDto("1", "username", "password", "email", Role.USER);

        Mockito.when(accountRepository.findById("1")).thenReturn(Optional.of(account));
        Mockito.when(converter.accountDtoConverter(account)).thenReturn(accountDto);

        AccountDto result = service.getAccountById("1");

        assertEquals(result, accountDto);
    }
}