package com.example.bookauthorapp.service;

import com.example.bookauthorapp.entity.Author;
import com.example.bookauthorapp.entity.Book;
import com.example.bookauthorapp.repository.AuthorRepository;
import com.example.bookauthorapp.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // --- Author Operations ---

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public Author saveAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Integrity violation: Could not save author.", e);
        }
    }

    // --- Book Operations ---

    public List<Book> getAllBooksWithAuthors() {
        // Using custom inner join query
        return bookRepository.findAllBooksWithAuthors();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Integrity violation: Could not save book. Please ensure the author exists.", e);
        }
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
            existingBook.setAuthor(updatedBook.getAuthor());
            try {
                return bookRepository.save(existingBook);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Integrity violation: Could not update book.", e);
            }
        }).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }
}
