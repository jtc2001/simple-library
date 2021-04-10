package com.artcoffer.library.service;

import com.artcoffer.library.model.BookEntity;
import com.artcoffer.library.model.dto.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookMapperTest {

    @Test
    public void testMapEntityToBook() {
        var bookEntity = new BookEntity(1L, "Shoeless Joe", "Shoeless Joe Jackson", 324, "SciFi");
        var expectedBook = new Book(1L, "Shoeless Joe", "Shoeless Joe Jackson", 324, "SciFi");
        assertEquals(expectedBook, BookMapper.mapFromEntity(bookEntity));
    }

    @Test
    public void testMapEntityToBookNullBookEntity() {
        assertThrows(NullPointerException.class, () -> BookMapper.mapFromEntity(null));
    }

}
