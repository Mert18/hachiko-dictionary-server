package com.m2t.hachikodictionary.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.m2t.hachikodictionary.dto.account.AccountDto;
import com.m2t.hachikodictionary.dto.account.AccountDtoConverter;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.AccountNotFoundException;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Role;
import com.m2t.hachikodictionary.repository.AccountRepository;
import com.m2t.hachikodictionary.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private AccountService service;
    private AccountRepository accountRepository;
    private AccountDtoConverter converter;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        converter =  Mockito.mock(AccountDtoConverter.class);
        service = new AccountService(accountRepository, converter);
        objectMapper = new ObjectMapper();
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

        assertThrows(AccountNotFoundException.class, () -> service.findAccountById("1"));
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

    @Test
    public void testFindAccountByUsername_whenAccountExists_shouldReturnAccount() {
        Account account = new Account("1", "username", "password", "email", Role.USER);
        Mockito.when(accountRepository.findAccountByUsername("username")).thenReturn(account);
        Account result = service.findAccountByUsername("username");

        assertEquals(result, account);
    }

    @Test
    public void testFindAccountByUsername_whenAccountDoesNotExist_shouldThrowAccountNotFoundException() {
        Mockito.when(accountRepository.findAccountByUsername("username")).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> service.findAccountByUsername("username"));
    }

    @Test
    public void testLoadUserByEmail_whenAccountExists_shouldReturnAccount() {
        Account account = new Account("1", "username", "password", "email", Role.USER);
        Mockito.when(accountRepository.findAccountByEmail("email")).thenReturn(account);
        Account result = service.loadUserByEmail("email");

        assertEquals(result, account);
    }

    @Test
    public void testLoadUserByEmail_whenAccountDoesNotExist_shouldThrowAccountNotFoundException() {
        Mockito.when(accountRepository.findAccountByEmail("email")).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> service.loadUserByEmail("email"));
    }

    @Test
    public void testConfirmAccount_whenAccountExists_shouldUpdateAccountConfirmedStatus() {
        // Arrange
        Account existingAccount = new Account("1", "username", "password", "email", Role.USER, false);
        Mockito.when(accountRepository.findAccountByEmail("email")).thenReturn(existingAccount);

        // Act
        service.confirmAccount("email");

        // Assert
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        Mockito.verify(accountRepository).save(accountCaptor.capture());
        Account capturedAccount = accountCaptor.getValue();

        // Assert
        assertTrue(capturedAccount.getConfirmed());
    }

    @Test
    public void testConfirmAccount_whenAccountDoesNotExist_shouldThrowAccountNotFoundException() {
        Mockito.when(accountRepository.findAccountByEmail("email")).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> service.confirmAccount("email"));
    }

    @Test
    public void testIsAccountConfirmed_whenAccountExists_shouldReturnConfirmedResponse() {
        // Setup
        Account existingAccount = new Account("1", "username", "password", "email", Role.USER, true);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("confirmed", existingAccount.getConfirmed());
        jsonObject.put("email", existingAccount.getEmail());
        jsonObject.put("id", existingAccount.getId());
        Response expectedResponse = new Response(true, "Check is completed.", jsonObject);

        // Act
        Mockito.when(accountRepository.findById("1")).thenReturn(Optional.of(existingAccount));
        Response actualResponse = service.isAccountConfirmed("1");

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testIsAccountConfirmed_whenAccountDoesNotExist_shouldThrowAccountNotFoundException() {
        Mockito.when(accountRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.isAccountConfirmed("1"));
    }


}