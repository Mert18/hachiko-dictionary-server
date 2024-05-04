package com.m2t.hachikodictionary.unit;

import com.m2t.hachikodictionary.client.WordnikClient;
import com.m2t.hachikodictionary.dto.client.WordAudio;
import com.m2t.hachikodictionary.dto.word.CreateWordRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.dto.word.WordDto;
import com.m2t.hachikodictionary.dto.word.WordDtoConverter;
import com.m2t.hachikodictionary.exception.WordAlreadyExistsException;
import com.m2t.hachikodictionary.exception.WordNotFoundException;
import com.m2t.hachikodictionary.model.Word;
import com.m2t.hachikodictionary.repository.WordPagingRepository;
import com.m2t.hachikodictionary.repository.WordRepository;
import com.m2t.hachikodictionary.service.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WordServiceTest {
    private WordService service;
    private WordRepository wordRepository;
    private WordPagingRepository wordPagingRepository;
    private WordDtoConverter wordDtoConverter;
    private WordnikClient wordnikClient;

    @BeforeEach
    public void setUp() {
        wordRepository = Mockito.mock(WordRepository.class);
        wordPagingRepository = Mockito.mock(WordPagingRepository.class);
        wordDtoConverter = Mockito.mock(WordDtoConverter.class);
        wordnikClient = Mockito.mock(WordnikClient.class);
        service = new WordService(wordRepository, wordDtoConverter, wordPagingRepository, wordnikClient);
    }

    @Test
    public void testFindWordById_whenWordExists_shouldReturnWord() {
        // Arrange
        Word word = new Word("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        WordDto wordDto = new WordDto("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null, null);
        Response expectedResponse = new Response(true, "Word retrieval successful.", wordDto, false);

        Mockito.when(wordRepository.findById("1")).thenReturn(Optional.of(word));
        Mockito.when(wordDtoConverter.wordDtoConverter(word)).thenReturn(wordDto);
        Mockito.when(wordnikClient.getWordAudio(any())).thenReturn(null);

        // Act
        Response result = service.getWord("1");

        // Assert
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.getData(), result.getData());
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());

        verify(wordRepository, times(1)).findById("1");
        verify(wordDtoConverter, times(1)).wordDtoConverter(word);
    }

    @Test
    public void testFindWordById_whenWordDoesNotExist_shouldThrowWordNotFoundException() {
        // Arrange
        Mockito.when(wordRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(WordNotFoundException.class, () -> service.findWordById("1"));
        verify(wordRepository, times(1)).findById("1");
    }

    @Test
    public void testGetWordByTitle_whenWordExists_shouldReturnWord() {
        // Arrange
        Word word = new Word("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        WordDto wordDto = new WordDto("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null, null);

        Mockito.when(wordRepository.findWordByTitle("title")).thenReturn(word);
        Mockito.when(wordDtoConverter.wordDtoConverter(word)).thenReturn(wordDto);

        // Act
        Response result = service.getWordByTitle("title");

        // Assert
        assertEquals("Word retrieval successful.", result.getMessage());
        assertEquals(wordDto, result.getData());

        verify(wordRepository, times(1)).findWordByTitle("title");
        verify(wordDtoConverter, times(1)).wordDtoConverter(word);
    }

    @Test
    public void testGetWordByTitle_whenWordDoesNotExist_shouldThrowWordNotFoundException() {
        // Arrange
        Mockito.when(wordRepository.findWordByTitle("title")).thenReturn(null);

        // Act & Assert
        assertThrows(WordNotFoundException.class, () -> service.getWordByTitle("title"));
        verify(wordRepository, times(1)).findWordByTitle("title");
    }

    @Test
    public void testGetAllWords_whenSuccessful_returnAllWords() {
        // Arrange
        Word word = new Word("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        List<Word> wordList = new ArrayList<>();
        wordList.add(word);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Word> wordPage = new PageImpl<>(wordList, pageRequest, wordList.size());

        Response expectedResponse = new Response(true, "Word retrieval successful.", wordPage, false);
        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(wordPagingRepository.findAll(pageable)).thenReturn(wordPage);

        // Act
        Response result = service.getAllWords(pageable);

        // Assert
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.getData(), result.getData());
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());

        verify(wordPagingRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetOneWordByDifficulty_whenWordExists_shouldReturnWord() {
        // Arrange
        Word word = new Word("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        WordDto wordDto = new WordDto("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null, null);
        Response expectedResponse = new Response(true, "Random word retrieval successful.", wordDto, false);

        Mockito.when(wordRepository.findRandomWord()).thenReturn(word);
        Mockito.when(wordDtoConverter.wordDtoConverter(word)).thenReturn(wordDto);

        // Act
        Response result = service.getRandomWord();

        // Assert
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.getData(), result.getData());
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());

        verify(wordRepository, times(1)).findRandomWord();
        verify(wordDtoConverter, times(1)).wordDtoConverter(word);
    }

    @Test
    public void testGetOneWordByDifficulty_whenWordDoesNotExist_shouldThrowWordNotFoundException() {
        // Arrange
        Mockito.when(wordRepository.findRandomWord()).thenReturn(null);

        // Act & Assert
        assertThrows(WordNotFoundException.class, () -> service.getRandomWord());
        verify(wordRepository, times(1)).findRandomWord();
    }

    @Test
    public void testCreateWord_whenWordDoesNotExist_shouldCreateWord() {
        // Arrange
        Word word = new Word("newWord", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        CreateWordRequest createWordRequest = new CreateWordRequest("newWord", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null);
        Response expectedResponse = new Response(true, "Word creation successful.", word);

        Mockito.when(wordRepository.existsByTitle(anyString())).thenReturn(false);
        Mockito.when(wordRepository.save(any(Word.class))).thenReturn(word);

        // Act
        Response result = service.createWord(createWordRequest);

        // Assert
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.getData(), result.getData());
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());

        verify(wordRepository, times(1)).existsByTitle("newWord");
        verify(wordRepository, times(1)).save(any(Word.class));
    }

    @Test
    public void testCreateWord_whenWordExists_shouldThrowWordAlreadyExistsException() {
        // Arrange
        CreateWordRequest createWordRequest = new CreateWordRequest("newWord", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null);

        Mockito.when(wordRepository.existsByTitle(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(WordAlreadyExistsException.class, () -> service.createWord(createWordRequest));
        verify(wordRepository, times(1)).existsByTitle("newWord");
    }

    @Test
    public void testUpdateWord_whenWordExists_shouldUpdateWord() {
        // Arrange
        CreateWordRequest createWordRequest = new CreateWordRequest("newTitle", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null);
        Word word = new Word("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        WordDto wordDto = new WordDto("1", "newTitle", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null, null);

        Response expectedResponse = new Response(true, "Word update successful.", wordDto, false);

        Mockito.when(wordRepository.findById("1")).thenReturn(Optional.of(word));
        Mockito.when(wordDtoConverter.wordDtoConverter(word)).thenReturn(wordDto);

        // Act
        Response result = service.updateWord("1", createWordRequest);

        // Assert
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.getData(), result.getData());
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());

        verify(wordRepository, times(1)).findById("1");
        verify(wordRepository, times(1)).save(any(Word.class));
        verify(wordDtoConverter, times(1)).wordDtoConverter(word);
    }

    @Test
    public void testUpdateWord_whenWordDoesNotExist_shouldThrowWordNotFoundException() {
        // Arrange
        CreateWordRequest createWordRequest = new CreateWordRequest("newTitle", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null);

        Mockito.when(wordRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(WordNotFoundException.class, () -> service.updateWord("1", createWordRequest));
        verify(wordRepository, times(1)).findById("1");
    }

    @Test
    public void deleteWord_whenWordExists_shouldDeleteWord() {
        // Arrange
        Word word = new Word("1", "title", "verb", "medium", Set.of(), Set.of(), Set.of(), Set.of(), null, null);
        Response expectedResponse = new Response(true, "Word deletion successful.", false);

        Mockito.when(wordRepository.findById("1")).thenReturn(Optional.of(word));

        // Act
        Response result = service.deleteWord("1");

        // Assert
        assertEquals(expectedResponse.getMessage(), result.getMessage());
        assertEquals(expectedResponse.getSuccess(), result.getSuccess());

        verify(wordRepository, times(1)).findById("1");
        verify(wordRepository, times(1)).delete(any(Word.class));
    }

    @Test
    public void deleteWord_whenWordDoesNotExist_shouldThrowWordNotFoundException() {
        // Arrange
        Mockito.when(wordRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(WordNotFoundException.class, () -> service.deleteWord("1"));
        verify(wordRepository, times(1)).findById("1");
    }

}
