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
        Mockito.when(quoteRepository.getRandomQuote(anyString())).thenReturn(quote);
        Response expectedResponse = new Response(true, "Quote retrieved successfully.", quote, false);

        // Act
        Response response = service.getRandomQuote(anyString());

        // Assert
        assertEquals(expectedResponse.getData(), response.getData());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertEquals(expectedResponse.getSuccess(), response.getSuccess());

        verify(quoteRepository, times(1)).getRandomQuote(anyString());
    }

    @Test
    public void testGetRandomQuote_whenQuoteNotFound_shouldThrowQuoteNotFoundException() {
        // Arrange
        Mockito.when(quoteRepository.getRandomQuote(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(QuoteNotFoundException.class, () -> service.getRandomQuote(anyString()));

        verify(quoteRepository, times(1)).getRandomQuote(anyString());
    }

    @Test
    public void testCreateQuote_whenSuccessful_shouldReturnCreatedQuote() {
        // Arrange
        CreateQuoteRequest createQuoteRequest = new CreateQuoteRequest("test quote", "test author", "medium");
        Quote quote = new Quote("test quote", "test author", "medium");
        Response expectedResponse = new Response(true, "Quote created successfully.", quote);

        // Act
        Response response = service.createQuote(createQuoteRequest);

        // Assert
        assertEquals(expectedResponse.getData(), response.getData());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertEquals(expectedResponse.getSuccess(), response.getSuccess());

        verify(quoteRepository, times(1)).save(any(Quote.class));
    }

    @Test
    public void testDeleteQuote_whenSuccessful_shouldReturnSuccessfullyDeletedResponse() {
        // Arrange
        Response expectedResponse = new Response(true, "Quote deleted successfully.");

        // Act
        Response response = service.deleteQuote(anyString());

        // Assert
        assertEquals(expectedResponse.getData(), response.getData());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        assertEquals(expectedResponse.getSuccess(), response.getSuccess());

        verify(quoteRepository, times(1)).deleteById(anyString());
    }
}
