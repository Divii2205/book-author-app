package com.example.bookauthor.service;

import com.example.bookauthor.dto.BookAuthorView;
import com.example.bookauthor.model.Author;
import com.example.bookauthor.model.Book;
import com.example.bookauthor.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BookAuthorView> findAllWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
    }

    @Transactional
    public Book save(Book book, Long authorId) {
        if (book.getId() == null && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DataIntegrityViolationException(
                    "A book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        Author author = authorService.findById(authorId);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, Book updated, Long authorId) {
        Book existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setIsbn(updated.getIsbn());
        existing.setGenre(updated.getGenre());
        existing.setPrice(updated.getPrice());
        existing.setAuthor(authorService.findById(authorId));
        return bookRepository.save(existing);
    }
}
