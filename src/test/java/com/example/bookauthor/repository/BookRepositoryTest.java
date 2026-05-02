package com.example.bookauthor.repository;

import com.example.bookauthor.dto.BookAuthorView;
import com.example.bookauthor.model.Author;
import com.example.bookauthor.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findAllBooksWithAuthors_returnsJoinedRows() {
        Author orwell = authorRepository.save(new Author("George Orwell", "go@test.com", "British"));
        Author austen = authorRepository.save(new Author("Jane Austen", "ja@test.com", "British"));

        bookRepository.save(new Book("1984", "ISBN-001", "Dystopian", new BigDecimal("9.99"), orwell));
        bookRepository.save(new Book("Animal Farm", "ISBN-002", "Satire", new BigDecimal("7.99"), orwell));
        bookRepository.save(new Book("Pride and Prejudice", "ISBN-003", "Romance", new BigDecimal("8.99"), austen));

        List<BookAuthorView> rows = bookRepository.findAllBooksWithAuthors();

        assertThat(rows).hasSize(3);
        assertThat(rows).extracting(BookAuthorView::getTitle)
                .containsExactlyInAnyOrder("1984", "Animal Farm", "Pride and Prejudice");
        assertThat(rows).allSatisfy(r -> assertThat(r.getAuthorName()).isNotBlank());
    }

    @Test
    void existsByIsbn_detectsDuplicates() {
        Author a = authorRepository.save(new Author("Test Author", "t@test.com", "X"));
        bookRepository.save(new Book("Some Book", "ISBN-DUP", "Genre", new BigDecimal("1.00"), a));

        assertThat(bookRepository.existsByIsbn("ISBN-DUP")).isTrue();
        assertThat(bookRepository.existsByIsbn("ISBN-NOPE")).isFalse();
    }
}
