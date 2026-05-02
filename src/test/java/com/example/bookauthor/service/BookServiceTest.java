package com.example.bookauthor.service;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.model.Book;
import com.example.bookauthor.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("Author", "a@test.com", "X");
        author.setId(1L);
        book = new Book("Title", "ISBN-1", "Genre", new BigDecimal("10.00"), null);
    }

    @Test
    void save_assignsAuthorAndPersists() {
        when(bookRepository.existsByIsbn("ISBN-1")).thenReturn(false);
        when(authorService.findById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book saved = bookService.save(book, 1L);

        assertThat(saved.getAuthor()).isEqualTo(author);
        verify(bookRepository).save(book);
    }

    @Test
    void save_throwsWhenIsbnDuplicate() {
        when(bookRepository.existsByIsbn("ISBN-1")).thenReturn(true);

        assertThatThrownBy(() -> bookService.save(book, 1L))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("already exists");

        verify(bookRepository, never()).save(any());
    }

    @Test
    void update_overwritesFieldsAndReassignsAuthor() {
        Book existing = new Book("Old", "ISBN-OLD", "Old", new BigDecimal("1.00"), author);
        existing.setId(5L);
        Author newAuthor = new Author("Other", "o@test.com", "Y");
        newAuthor.setId(2L);

        when(bookRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(authorService.findById(2L)).thenReturn(newAuthor);
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book updated = new Book("New", "ISBN-NEW", "Fiction", new BigDecimal("5.50"), null);
        Book result = bookService.update(5L, updated, 2L);

        assertThat(result.getTitle()).isEqualTo("New");
        assertThat(result.getIsbn()).isEqualTo("ISBN-NEW");
        assertThat(result.getAuthor()).isEqualTo(newAuthor);
    }

    @Test
    void findById_throwsWhenMissing() {
        when(bookRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(404L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Book not found");
    }
}
