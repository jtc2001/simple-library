package com.artcoffer.library.service;

import com.artcoffer.library.model.BookEntity;
import com.artcoffer.library.model.dto.Book;

import static com.google.common.base.Preconditions.checkNotNull;

public class BookMapper {

    public static Book mapFromEntity(BookEntity bookEntity) {
        checkNotNull(bookEntity, "bookEntity cannot be null");
        return new Book(
                bookEntity.getId(),
                bookEntity.getTitle(),
                bookEntity.getDescription(),
                bookEntity.getPageCount(),
                bookEntity.getCategory());
    }

}
