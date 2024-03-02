package com.m2t.hachikodictionary.unit;

import com.m2t.hachikodictionary.dto.quote.CreateQuoteRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.QuoteNotFoundException;
import com.m2t.hachikodictionary.model.Quote;
import com.m2t.hachikodictionary.repository.QuoteRepository;
import com.m2t.hachikodictionary.service.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuoteServiceTest {
    private QuoteService service;
    private QuoteRepository quoteRepository;

    @BeforeEach
    public void setUp() {
        quoteRepository = Mockito.mock(QuoteRepository.class);
        service = new QuoteService(quoteRepository);
    }

    @Test
    public void testGetRandomQuote_whenSuccessful_shouldReturnRandomQuote() {
        // Arrange
        Quote quote = new Quote("test quote", "test author", "medium");
        Mockito.when(quoteRepository.getRandomQuote()).thenReturn(quote);
        Response expectedResponse = new Response(true, "Quote retrieved successfully.", quote, false);

        // Act
        Response response = service.getRandomQuote();

        // Assert
        assertEquals(expectedResponse.getData(), response.getData());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertEquals(expectedResponse.getSuccess(), response.getSuccess());

        verify(quoteRepository, times(1)).getRandomQuote();
    }

    @Test
    public void testGetRandomQuote_whenQuoteNotFound_shouldThrowQuoteNotFoundException() {
        // Arrange
        Mockito.when(quoteRepository.getRandomQuote()).thenReturn(null);

        // Act & Assert
        assertThrows(QuoteNotFoundException.class, () -> service.getRandomQuote());

        verify(quoteRepository, times(1)).getRandomQuote();
    }
}
