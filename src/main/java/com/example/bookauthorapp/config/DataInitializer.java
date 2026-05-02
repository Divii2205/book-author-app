package com.example.bookauthorapp.config;

import com.example.bookauthorapp.entity.Author;
import com.example.bookauthorapp.entity.Book;
import com.example.bookauthorapp.repository.AuthorRepository;
import com.example.bookauthorapp.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create 10 Authors
        List<Author> authors = Arrays.asList(
                new Author("J.K. Rowling", "British"),
                new Author("George R.R. Martin", "American"),
                new Author("J.R.R. Tolkien", "British"),
                new Author("Agatha Christie", "British"),
                new Author("Stephen King", "American"),
                new Author("Isaac Asimov", "American"),
                new Author("Arthur C. Clarke", "British"),
                new Author("Jane Austen", "British"),
                new Author("Charles Dickens", "British"),
                new Author("Mark Twain", "American")
        );

        authorRepository.saveAll(authors);

        // Fetch authors to assign to books
        List<Author> savedAuthors = authorRepository.findAll();

        // Create 10 Books
        List<Book> books = Arrays.asList(
                new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 1997, savedAuthors.get(0)),
                new Book("A Game of Thrones", "Fantasy", 1996, savedAuthors.get(1)),
                new Book("The Fellowship of the Ring", "Fantasy", 1954, savedAuthors.get(2)),
                new Book("Murder on the Orient Express", "Mystery", 1934, savedAuthors.get(3)),
                new Book("The Shining", "Horror", 1977, savedAuthors.get(4)),
                new Book("Foundation", "Science Fiction", 1951, savedAuthors.get(5)),
                new Book("2001: A Space Odyssey", "Science Fiction", 1968, savedAuthors.get(6)),
                new Book("Pride and Prejudice", "Romance", 1813, savedAuthors.get(7)),
                new Book("A Tale of Two Cities", "Historical Fiction", 1859, savedAuthors.get(8)),
                new Book("The Adventures of Tom Sawyer", "Adventure", 1876, savedAuthors.get(9))
        );

        bookRepository.saveAll(books);
    }
}
