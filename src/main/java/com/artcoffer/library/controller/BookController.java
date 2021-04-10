package com.artcoffer.library.controller;

import com.artcoffer.library.model.dto.Book;
import com.artcoffer.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    private final ResultResponseResolver resultResponseResolver;

    @Autowired
    public BookController(BookService bookService, ResultResponseResolver resultResponseResolver) {
        this.bookService = bookService;
        this.resultResponseResolver = resultResponseResolver;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return resultResponseResolver.resolve(bookService.getById(id));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAll() {
        return resultResponseResolver.resolve(bookService.getAll());
    }

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return resultResponseResolver.resolve(bookService.create(book));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return resultResponseResolver.resolve(bookService.update(id, book));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> removeBook(@PathVariable Long id) {
        return resultResponseResolver.resolve(bookService.delete(id));
    }

}
