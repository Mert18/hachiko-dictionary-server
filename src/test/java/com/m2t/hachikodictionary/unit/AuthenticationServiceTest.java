package com.m2t.hachikodictionary.unit;

import com.m2t.hachikodictionary.config.JWTService;
import com.m2t.hachikodictionary.dto.AuthenticationResponse;
import com.m2t.hachikodictionary.dto.LoginRequest;
import com.m2t.hachikodictionary.dto.RegistrationRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.*;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.model.Role;
import com.m2t.hachikodictionary.repository.AccountRepository;
import com.m2t.hachikodictionary.service.AccountService;
import com.m2t.hachikodictionary.service.AuthenticationService;
import com.m2t.hachikodictionary.service.ConfirmationService;
import com.m2t.hachikodictionary.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class AuthenticationServiceTest {
    private AuthenticationService service;
    private AccountRepository accountRepository;
    private AccountService accountService;
    private MailService mailService;
    private ConfirmationService confirmationService;
    private JWTService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService = Mockito.mock(AccountService.class);
        mailService = Mockito.mock(MailService.class);
        confirmationService = Mockito.mock(ConfirmationService.class);
        jwtService = Mockito.mock(JWTService.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        service = new AuthenticationService(accountRepository, accountService, jwtService, authenticationManager, passwordEncoder, mailService, confirmationService);
    }

    @Test
    public void testRegister_whenSuccessful_shouldReturnSuccessfulResponse() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest("testuser",
                "testpassword",
                "testpassword",
                "testuser@gmail.com");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("accessToken",
                "refreshToken",
                "bearer",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                true,
                false,
                "testuser",
                "testuser@gmail.com",
                Role.USER);

        when(jwtService.generateToken(any(Account.class))).thenReturn(authenticationResponse);

        // Act
        Response response = service.register(registrationRequest);

        // Assert
        verify(passwordEncoder, times(1)).encode(anyString());
        assertEquals(Boolean.TRUE, response.getSuccess());
        assertEquals("Registration successful.", response.getMessage());
        assertNotNull(response.getData());

        // Verify repository interactions if applicable
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testRegister_whenPasswordsDoNotMatch_shouldThrowPasswordsDoNotMatchException() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "testuser",
                "testpassword",
                "testpasword",
                "testuser@gmail.com"
        );

        // Act & Assert
        assertThrows(PasswordsDoNotMatchException.class, () -> service.register(registrationRequest));
    }

    @Test
    public void testRegister_whenUsernameExists_shouldThrowUsernameAlreadyExistsException() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "testuser",
                "testpassword",
                "testpassword",
                "testuser@gmail.com"
        );
        when(accountRepository.existsByUsername(anyString())).thenReturn(true);
        // Act & Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> service.register(registrationRequest));
    }

    @Test
    public void testRegister_whenEmailExists_shouldThrowEmailAlreadyExistsException() {
        // Arrange
        RegistrationRequest registrationRequest = new RegistrationRequest(
                "testuser",
                "testpassword",
                "testpassword",
                "testuser@gmail.com"
        );
        // Act
        when(accountRepository.existsByEmail(anyString())).thenReturn(true);
        // Assert
        assertThrows(EmailAlreadyExistsException.class, () -> service.register(registrationRequest));
    }

    @Test
    public void testLogin_whenSuccessful_shouldReturnSuccessfulResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest(
                "testuser@gmail.com",
                "testpassword"
        );
        Account account = new Account("1", "testuser", "testpassword", "testuser@gmail.com", Role.USER, true);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("accessToken",
                "refreshToken",
                "bearer",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                false,
                "testuser",
                "testuser@gmail.com",
                Role.USER);
            when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
            when(accountService.loadUserByEmail(anyString())).thenReturn(account);
            when(jwtService.generateToken(any(Account.class))).thenReturn(authenticationResponse);
        // Act
        Response response = service.login(loginRequest);

        // Assert
        assertEquals(Boolean.TRUE, response.getSuccess());
        assertEquals("Login successful.", response.getMessage());
        assertNotNull(response.getData());

        verify(jwtService, times(1)).generateToken(any(Account.class));
    }

    @Test
    public void testLogin_whenPasswordsDoNotMatch_shouldThrowInvalidCredentialsException() throws Exception {
        // Arrange
        Account account = new Account("1", "testuser", "testpassword", "testuser@gmail.com", Role.USER, true);
        LoginRequest loginRequest = new LoginRequest(
                "testuser@gmail.com",
                "testpassword"
        );

        when(accountService.loadUserByEmail(anyString())).thenReturn(account);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> service.login(loginRequest));
    }

    @Test
    public void testLogin_whenAccountDoesNotExist_shouldThrowAccountNotFoundException() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest(
                "testuser@gmail.com",
                "testpassword"
        );
        when(accountService.loadUserByEmail(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> service.login(loginRequest));
    }

    @Test
    public void testRefreshToken_whenSuccessful_shouldReturnSuccessfulResponse() {
        // Arrange
        Account account = new Account("1", "testuser", "testpassword", "testuser@gmail.com", Role.USER, true);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("accessToken",
                "refreshToken",
                "bearer",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                false,
                "testuser",
                "testuser@gmail.com",
                Role.USER);
        Response response = new Response(true, "Token refreshed.");
        when(jwtService.extractUsername(anyString())).thenReturn("testuser");
        when(accountService.loadUserByUsername(anyString())).thenReturn(account);
        when(jwtService.isTokenValid(anyString(), any(Account.class))).thenReturn(true);
        when(jwtService.generateToken(any(Account.class))).thenReturn(authenticationResponse);

        // Act
        Response actualResponse = service.refreshToken(anyString());

        // Assert
        assertEquals(actualResponse, response);
        assertNotNull(actualResponse.getData());

        verify(jwtService, times(1)).extractUsername(anyString());
        verify(jwtService, times(1)).isTokenValid(anyString(), any(Account.class));
        verify(accountService, times(1)).loadUserByUsername(anyString());
        verify(jwtService, times(1)).generateToken(any(Account.class));
    }

    @Test
    public void testRefreshToken_whenTokenIsInvalid_shouldThrowInvalidTokenException() {
        // Arrange
        Account account = new Account("1", "testuser", "testpassword", "testuser@gmail.com", Role.USER, true);
        when(jwtService.extractUsername(anyString())).thenReturn("testuser");
        when(accountService.loadUserByUsername(anyString())).thenReturn(account);
        when(jwtService.isTokenValid(anyString(), any(Account.class))).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> service.refreshToken(anyString()));
        verify(jwtService, times(1)).extractUsername(anyString());
        verify(accountService, times(1)).loadUserByUsername(anyString());
        verify(jwtService, times(1)).isTokenValid(anyString(), any(Account.class));
    }
}
