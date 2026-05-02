package com.example.bookauthor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(name = "uk_book_isbn", columnNames = "isbn")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String isbn;

    @Column(length = 50)
    private String genre;

    @Column(precision = 8, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Author is required")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_book_author"))
    private Author author;

    public Book() {
    }

    public Book(String title, String isbn, String genre, BigDecimal price, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.price = price;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}
