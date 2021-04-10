package com.artcoffer.library.repository;

import com.artcoffer.library.dao.BookRepository;
import com.artcoffer.library.model.BookEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTestIT {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void teardown() {
        bookRepository.deleteAll();
    }


    @Test
    public void testFindAll() {
        BookEntity jurassicPark = new BookEntity(1L, "Jurassic Park", "Dinosaurs", 448, "Science Fiction");
        BookEntity tinkerTailor = new BookEntity(2L, "Tinker Tailor Soldier Spy", "In Depth Spy Novel", 477, "Spy Novel");
        bookRepository.save(jurassicPark);
        bookRepository.save(tinkerTailor);

        var books = (List<BookEntity>) bookRepository.findAll();

        assertThat(books, Matchers.containsInAnyOrder(jurassicPark, tinkerTailor));
    }

    @Test
    public void testSaveExisting() {
        BookEntity jurassicPark = new BookEntity(1L, "Jurassic Park", "Dinosaurs", 448, "Science Fiction");
        bookRepository.save(jurassicPark);
        var books = (List<BookEntity>) bookRepository.findAll();
        var book = books.get(0);
        assertEquals(jurassicPark.getPageCount(), book.getPageCount());
        assertEquals(jurassicPark.getTitle(), book.getTitle());
        assertEquals(jurassicPark.getCategory(), book.getCategory());
        assertEquals(jurassicPark.getDescription(), book.getDescription());
    }

    @Test
    public void testSaveNew() {
        BookEntity jurassicPark = new BookEntity(null, "Jurassic Park", "Dinosaurs", 448, "Science Fiction");
        bookRepository.save(jurassicPark);
        var books = (List<BookEntity>) bookRepository.findAll();
        var book = books.get(0);
        assertEquals(jurassicPark.getPageCount(), book.getPageCount());
        assertEquals(jurassicPark.getTitle(), book.getTitle());
        assertEquals(jurassicPark.getCategory(), book.getCategory());
        assertEquals(jurassicPark.getDescription(), book.getDescription());
    }

    @Test
    public void testUpdate() {
        BookEntity jurassicPark = new BookEntity(null, "Jurassic Park", "Dinosaurs", 448, "Science Fiction");
        bookRepository.save(jurassicPark);
        var books = (List<BookEntity>) bookRepository.findAll();
        var savedBook = books.get(0);
        savedBook.setTitle("Tinker Tailor Soldier Spy");
        savedBook.setDescription("In depth spy novel");
        savedBook.setPageCount(476);
        savedBook.setCategory("Spy Novel");
        bookRepository.save(savedBook);
        var updatedBook = bookRepository.findById(savedBook.getId());
        assertEquals("Tinker Tailor Soldier Spy", updatedBook.get().getTitle());
        assertEquals("In depth spy novel", updatedBook.get().getDescription());
        assertEquals(476, updatedBook.get().getPageCount());
        assertEquals("Spy Novel", updatedBook.get().getCategory());
    }

    @Test
    public void testDelete() {
        BookEntity jurassicPark = new BookEntity(1L, "Jurassic Park", "Dinosaurs", 448, "Science Fiction");
        BookEntity tinkerTailor = new BookEntity(2L, "Tinker Tailor Soldier Spy", "In Depth Spy Novel", 477, "Spy Novel");
        var savedBook1 = bookRepository.save(jurassicPark);
        var savedBook2 = bookRepository.save(tinkerTailor);
        var books = (List<BookEntity>) bookRepository.findAll();
        assertEquals(2, books.size());
        bookRepository.delete(jurassicPark);
        var booksAfterDeletion = (List<BookEntity>) bookRepository.findAll();
        assertEquals(1, booksAfterDeletion.size());
    }
}