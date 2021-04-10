package com.artcoffer.library.service;

import com.artcoffer.library.dao.BookRepository;
import com.artcoffer.library.model.BookEntity;
import com.artcoffer.library.model.Result;
import com.artcoffer.library.model.dto.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private BookService bookService;

    private BookEntity bookEntity;

    private Book book;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookService = new BookService(bookRepository);

        book = new Book(null, "Jurassic Park", "Scifi novel", 418, "SciFi");
        bookEntity = new BookEntity(1L, "Jurassic Park", "Scifi novel", 418, "SciFi");
    }

    @Test
    public void testGetAll() {
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        var result = bookService.getAll();
        assertEquals(result.getEntity(),
                List.of(new Book(
                        bookEntity.getId(),
                        bookEntity.getTitle(),
                        bookEntity.getDescription(),
                        bookEntity.getPageCount(),
                        bookEntity.getCategory())));
    }

    @Test
    public void testGetById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        var result = bookService.getById(1L);
        assertEquals(result.getEntity(),
                new Book(
                        bookEntity.getId(),
                        bookEntity.getTitle(),
                        bookEntity.getDescription(),
                        bookEntity.getPageCount(),
                        bookEntity.getCategory()));
    }

    @Test
    public void testCreate() {
        when(bookRepository.save(any())).thenReturn(bookEntity);
        var result = bookService.create(book);
        var expectedBook = new Book(bookEntity.getId(), "Jurassic Park", "Scifi novel", 418, "SciFi");
        assertEquals(expectedBook, result.getEntity());
        verify(bookRepository).save(any());
    }

    @Test
    public void testCreateThrowsException() {
        when(bookRepository.save(any())).thenThrow(new RuntimeException("Failed to save book"));
        var result = bookService.create(book);
        assertEquals(Result.ErrorType.UNKNOWN_ERROR, result.getErrorType());
    }

    @Test
    public void testUpdate() {
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        var result = bookService.update(1L, book);
        var expectedBook = new Book(1L, "Jurassic Park", "Scifi novel", 418, "SciFi");
        assertEquals(expectedBook, result.getEntity());
        verify(bookRepository).save(bookEntity);
    }

    @Test
    public void testUpdateBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        var result = bookService.update(1L, book);
        assertEquals(Result.ErrorType.NOT_FOUND, result.getErrorType());
        verify(bookRepository, never()).save(any());
    }

    @Test
    public void testUpdateThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(bookEntity)).thenThrow(new RuntimeException("Failed to update"));
        var result = bookService.update(1L, book);
        assertEquals(Result.ErrorType.UNKNOWN_ERROR, result.getErrorType());
        verify(bookRepository).save(bookEntity);
    }

    @Test
    public void testDelete() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        doNothing().when(bookRepository).delete(bookEntity);
        var result = bookService.delete(1L);
        assertEquals(false, result.hasErrors());
        verify(bookRepository).delete(bookEntity);
    }

    @Test
    public void testDeleteBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        var result = bookService.delete(1L);
        assertEquals(Result.ErrorType.NOT_FOUND, result.getErrorType());
        verify(bookRepository, never()).delete(any());
    }

}
