package com.artcoffer.library.service;

import com.artcoffer.library.dao.BookRepository;
import com.artcoffer.library.model.BookEntity;
import com.artcoffer.library.model.Result;
import com.artcoffer.library.model.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.artcoffer.library.service.BookMapper.mapFromEntity;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * @return Result<List<Book>> representing all books in the library or error
     */
    public Result<List<Book>> getAll() {
        List<BookEntity> bookEntities = (List<BookEntity>) bookRepository.findAll();
        return new Result<>(bookEntities.stream().map(BookMapper::mapFromEntity).collect(Collectors.toList()));
    }

    /**
     * @return Result<List<Book>> representing all books in the library or error
     */
    public Result<Book> getById(Long bookId) {
        var bookEntityOpt = bookRepository.findById(bookId);

        if(bookEntityOpt.isEmpty()) {
            logger.warn("Book not found for ID {} attempting to retrieve", bookId);
            return new Result<>(true, Result.ErrorType.NOT_FOUND, String.format("Book not found for Id %s", bookId));
        }

        var bookEntity = bookEntityOpt.get();
        return new Result<>(mapFromEntity(bookEntity));
    }

    /**
     * Adds a new book to the library
     * @param book to add
     * @return Result<Book> representing the created book or error
     */
    public Result<Book> create(Book book) {
        try {
            BookEntity bookEntity = new BookEntity(book.getId(), book.getTitle(), book.getDescription(), book.getPageCount(), book.getCategory());
            var savedBook = bookRepository.save(bookEntity);

            return new Result<>(mapFromEntity(savedBook));
        } catch (Exception e) {
            logger.error("Unexpected error creating a book", e);
            return new Result<>(true, Result.ErrorType.UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * Updates a given book by Id
     * @param bookId of the book to update
     * @param book to update
     * @return Result<Book> representing the updated book or error
     */
    public Result<Book> update(Long bookId, Book book) {
        try {
            var bookEntityOpt = bookRepository.findById(bookId);

            if (bookEntityOpt.isEmpty()) {
                logger.warn("Book not found for ID {} attempting to update", bookId);
                return new Result<>(true, Result.ErrorType.NOT_FOUND, String.format("Book not found for Id %s", bookId));
            }

            var bookEntity = bookEntityOpt.get();
            bookEntity.setTitle(book.getTitle());
            bookEntity.setCategory(book.getCategory());
            bookEntity.setDescription(book.getDescription());
            bookEntity.setPageCount(book.getPageCount());
            var savedBook = bookRepository.save(bookEntity);

            return new Result<>(mapFromEntity(savedBook));
        } catch (Exception e) {
            logger.error("Unexpected error updating a book", e);
            return new Result<>(true, Result.ErrorType.UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * Removes a given book from the library
     * @param bookId of the book to remove
     * @return Result<> representing success or failure of remove operaion
     */
    public Result<?> delete(Long bookId) {
        var book = bookRepository.findById(bookId);

        if(book.isEmpty()) {
            logger.warn("Book not found for ID {} attempting to delete", bookId);
            return new Result<>(true, Result.ErrorType.NOT_FOUND, String.format("Book not found for Id %s", bookId));
        }

        try {
            book.ifPresent(bookRepository::delete);
        }catch(Exception e) {
            logger.error("Unexpected error deleting a book", e);
            return new Result<>(true, Result.ErrorType.UNKNOWN_ERROR, e.getMessage());
        }

        return new Result<>(null);
    }

}
