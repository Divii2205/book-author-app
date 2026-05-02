package com.example.bookauthorapp.repository;

import com.example.bookauthorapp.entity.Author;
import com.example.bookauthorapp.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void testFindAllBooksWithAuthors() {
        Author author = new Author("Test Author", "Test Nationality");
        authorRepository.save(author);

        Book book = new Book("Test Title", "Test Genre", 2024, author);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllBooksWithAuthors();

        assertEquals(1, books.size());
        Book fetchedBook = books.get(0);
        assertNotNull(fetchedBook.getAuthor());
        assertEquals("Test Author", fetchedBook.getAuthor().getName());
    }
}
