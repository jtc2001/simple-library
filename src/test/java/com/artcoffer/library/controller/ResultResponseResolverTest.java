package com.artcoffer.library.controller;

import com.artcoffer.library.model.Result;
import com.artcoffer.library.model.dto.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultResponseResolverTest {

    private ResultResponseResolver resultResponseResolver;

    @BeforeEach
    public void setup() {
        resultResponseResolver = new ResultResponseResolver();
    }

    @Test
    public void testResolveSuccess() {
        var book = new Book(1L, "Jurassic Park", "Dinosaurs", 418, "SciFi");
        var result = new Result<Book>(book);
        assertEquals(ResponseEntity.ok(book), resultResponseResolver.resolve(result));
    }

    @Test
    public void testResolveNotFound() {
        var notFoundResult = new Result<>(true, Result.ErrorType.NOT_FOUND, "Not Found");
        assertEquals(ResponseEntity.notFound().build(), resultResponseResolver.resolve(notFoundResult));
    }

    @Test
    public void testResolveUnknownError() {
        var unkonwnErrorResult = new Result<>(true, Result.ErrorType.UNKNOWN_ERROR, "Fail");
        assertEquals(ResponseEntity.status(500).build(), resultResponseResolver.resolve(unkonwnErrorResult));
    }

}
