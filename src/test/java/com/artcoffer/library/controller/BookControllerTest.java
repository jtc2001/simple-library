package com.artcoffer.library.controller;

import com.artcoffer.library.model.Result;
import com.artcoffer.library.model.dto.Book;
import com.artcoffer.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private BookController bookController;

    @Mock
    private ResultResponseResolver resultResponseResolver;

    @Mock
    private BookService bookService;

    @Mock
    private Book book;

    @BeforeEach
    public void setup() {
        bookController = new BookController(bookService, resultResponseResolver);
    }

    @Test
    public void testGetAll(){
        when(resultResponseResolver.resolve(any())).thenReturn(ResponseEntity.ok(List.of(book)));
        when(bookService.getAll()).thenReturn(new Result<>(List.of(book)));
        var books = bookController.getAll();
        assertEquals(ResponseEntity.ok(List.of(book)), books);
        verify(resultResponseResolver).resolve(any());
    }

    @Test
    public void testGetById(){
        when(resultResponseResolver.resolve(any())).thenReturn(ResponseEntity.ok(book));
        when(bookService.getById(1L)).thenReturn(new Result<>(book));
        var result = bookController.getById(1L);
        assertEquals(ResponseEntity.ok(book), result);
        verify(resultResponseResolver).resolve(any());
    }

    @Test
    public void testGetAllWhenNoBooksReturnsEmptyList(){
        when(resultResponseResolver.resolve(any())).thenReturn(ResponseEntity.ok(List.of()));
        when(bookService.getAll()).thenReturn(new Result<>(List.of()));
        var books = bookController.getAll();
        assertEquals(ResponseEntity.ok(List.of()), books);
        verify(resultResponseResolver).resolve(any());
    }

    @Test
    public void testAddBook() {
        when(resultResponseResolver.resolve(any())).thenReturn(ResponseEntity.ok(book));
        when(bookService.create(any())).thenReturn(new Result<>(book));
        assertEquals(ResponseEntity.ok(book), bookController.addBook(book));
        verify(bookService).create(book);
        verify(resultResponseResolver).resolve(any());
    }

    @Test
    public void testUpdateBook() {
        when(resultResponseResolver.resolve(any())).thenReturn(ResponseEntity.ok(book));
        when(bookService.update(any(), any())).thenReturn(new Result<>(book));
        assertEquals(ResponseEntity.ok(book), bookController.updateBook(1L, book));
        verify(bookService).update(1L, book);
        verify(resultResponseResolver).resolve(any());
    }

    @Test
    public void testRemoveBook() {
        when(resultResponseResolver.resolve(any())).thenReturn(ResponseEntity.ok().build());
        when(bookService.delete(any())).thenReturn(new Result<>(null));
        assertEquals(ResponseEntity.ok().build(), bookController.removeBook(1L));
        verify(resultResponseResolver).resolve(any());
    }

}