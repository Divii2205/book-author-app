package com.example.bookauthorapp.service;

import com.example.bookauthorapp.entity.Author;
import com.example.bookauthorapp.entity.Book;
import com.example.bookauthorapp.repository.AuthorRepository;
import com.example.bookauthorapp.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LibraryService libraryService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("John Doe", "American");
        author.setId(1L);

        book = new Book("Sample Book", "Fiction", 2023, author);
        book.setId(1L);
    }

    @Test
    void testSaveAuthor_Success() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = libraryService.saveAuthor(author);

        assertNotNull(savedAuthor);
        assertEquals("John Doe", savedAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testSaveAuthor_IntegrityViolation() {
        when(authorRepository.save(any(Author.class))).thenThrow(DataIntegrityViolationException.class);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            libraryService.saveAuthor(author);
        });

        assertTrue(exception.getMessage().contains("Integrity violation"));
    }

    @Test
    void testGetAllBooksWithAuthors() {
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(book));

        List<Book> books = libraryService.getAllBooksWithAuthors();

        assertEquals(1, books.size());
        assertEquals("Sample Book", books.get(0).getTitle());
    }

    @Test
    void testUpdateBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedDetails = new Book("Updated Title", "Sci-Fi", 2024, author);
        Book updatedBook = libraryService.updateBook(1L, updatedDetails);

        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals(2024, updatedBook.getPublicationYear());
    }

    @Test
    void testUpdateBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Book updatedDetails = new Book("Updated Title", "Sci-Fi", 2024, author);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            libraryService.updateBook(1L, updatedDetails);
        });

        assertEquals("Book not found with id 1", exception.getMessage());
    }
}
