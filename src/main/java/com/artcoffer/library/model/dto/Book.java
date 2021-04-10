package com.artcoffer.library.model.dto;

import java.util.Objects;

public class Book {

    public Book(Long id, String title, String description, int pageCount, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.category = category;
    }

    private final Long id;

    private final String title;

    private final String description;

    private final int pageCount;

    private final String category;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pageCount == book.pageCount &&
                Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description) &&
                Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pageCount, category);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", category='" + category + '\'' +
                '}';
    }
}
