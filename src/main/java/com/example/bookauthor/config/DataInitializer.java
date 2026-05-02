package com.example.bookauthor.config;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.model.Book;
import com.example.bookauthor.repository.AuthorRepository;
import com.example.bookauthor.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(AuthorRepository authorRepo, BookRepository bookRepo) {
        return args -> {
            if (authorRepo.count() > 0) {
                return;
            }

            List<Author> authors = List.of(
                    new Author("George Orwell", "g.orwell@example.com", "British"),
                    new Author("J.K. Rowling", "jk.rowling@example.com", "British"),
                    new Author("Haruki Murakami", "h.murakami@example.com", "Japanese"),
                    new Author("Chimamanda Adichie", "c.adichie@example.com", "Nigerian"),
                    new Author("Gabriel Garcia Marquez", "g.marquez@example.com", "Colombian"),
                    new Author("Leo Tolstoy", "l.tolstoy@example.com", "Russian"),
                    new Author("Jane Austen", "j.austen@example.com", "British"),
                    new Author("Mark Twain", "m.twain@example.com", "American"),
                    new Author("Agatha Christie", "a.christie@example.com", "British"),
                    new Author("Paulo Coelho", "p.coelho@example.com", "Brazilian")
            );
            authorRepo.saveAll(authors);

            List<Book> books = List.of(
                    new Book("1984", "978-0451524935", "Dystopian", new BigDecimal("9.99"), authors.get(0)),
                    new Book("Harry Potter and the Sorcerer's Stone", "978-0590353427", "Fantasy", new BigDecimal("12.50"), authors.get(1)),
                    new Book("Norwegian Wood", "978-0375704024", "Fiction", new BigDecimal("11.20"), authors.get(2)),
                    new Book("Half of a Yellow Sun", "978-1400095209", "Historical", new BigDecimal("13.75"), authors.get(3)),
                    new Book("One Hundred Years of Solitude", "978-0060883287", "Magical Realism", new BigDecimal("14.00"), authors.get(4)),
                    new Book("War and Peace", "978-1400079988", "Historical", new BigDecimal("18.99"), authors.get(5)),
                    new Book("Pride and Prejudice", "978-0141439518", "Romance", new BigDecimal("8.99"), authors.get(6)),
                    new Book("Adventures of Huckleberry Finn", "978-0142437179", "Adventure", new BigDecimal("7.50"), authors.get(7)),
                    new Book("Murder on the Orient Express", "978-0062693662", "Mystery", new BigDecimal("10.25"), authors.get(8)),
                    new Book("The Alchemist", "978-0061122415", "Fiction", new BigDecimal("9.50"), authors.get(9))
            );
            bookRepo.saveAll(books);
        };
    }
}
