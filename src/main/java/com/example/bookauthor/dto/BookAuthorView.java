package com.example.bookauthor.dto;

import java.math.BigDecimal;

public class BookAuthorView {

    private Long bookId;
    private String title;
    private String isbn;
    private String genre;
    private BigDecimal price;
    private Long authorId;
    private String authorName;
    private String authorEmail;
    private String authorNationality;

    public BookAuthorView(Long bookId, String title, String isbn, String genre, BigDecimal price,
                          Long authorId, String authorName, String authorEmail, String authorNationality) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.price = price;
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authorNationality = authorNationality;
    }

    public Long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public BigDecimal getPrice() { return price; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
    public String getAuthorEmail() { return authorEmail; }
    public String getAuthorNationality() { return authorNationality; }
}
