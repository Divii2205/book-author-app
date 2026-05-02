package com.example.bookauthor.repository;

import com.example.bookauthor.dto.BookAuthorView;
import com.example.bookauthor.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    /**
     * Custom query performing an INNER JOIN between Book and Author.
     * Returns a flat projection of every book paired with its author's details.
     */
    @Query("""
           SELECT new com.example.bookauthor.dto.BookAuthorView(
               b.id, b.title, b.isbn, b.genre, b.price,
               a.id, a.name, a.email, a.nationality)
           FROM Book b
           INNER JOIN b.author a
           ORDER BY a.name ASC, b.title ASC
           """)
    List<BookAuthorView> findAllBooksWithAuthors();
}
