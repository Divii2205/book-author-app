# Book & Author Manager (Spring Boot + JSP + JPA)

A Spring Boot CRUD application that manages two related entities — **Authors** and **Books** — with a one-to-many relationship. The UI is rendered with JSP + JSTL and styled with plain CSS. Data is persisted via Spring Data JPA on an in-memory H2 database, automatically seeded with 10 rows in each table on startup.

---

## 1. Approach

### Entity Relationship

```
Author (1) ────< (M) Book
```

| Author        | Book                       |
|---------------|----------------------------|
| id (PK)       | id (PK)                    |
| name          | title                      |
| email (UK)    | isbn (UK)                  |
| nationality   | genre                      |
|               | price                      |
|               | author_id (FK -> author.id)|

The relationship is implemented with `@OneToMany(mappedBy = "author")` on `Author` and `@ManyToOne` + `@JoinColumn(name = "author_id")` on `Book`. Unique constraints on `email` and `isbn` enforce data integrity, and any violation surfaces as a `DataIntegrityViolationException` that the controller catches and shows on the form.

### Layered Design

| Layer        | Classes                                                               |
|--------------|-----------------------------------------------------------------------|
| Model        | `Author`, `Book`                                                      |
| DTO          | `BookAuthorView` (projection used by the join query)                  |
| Repository   | `AuthorRepository`, `BookRepository` (extends `JpaRepository`)        |
| Service      | `AuthorService`, `BookService`                                        |
| Controller   | `AuthorController`, `BookController`, `HomeController`, `GlobalExceptionHandler` |
| View         | `book-list.jsp`, `book-form.jsp`, `author-list.jsp`, `author-form.jsp`, `error.jsp` |
| Bootstrap    | `BookAuthorApplication`, `DataInitializer` (seeds 10 + 10 rows)       |

### Operations

1. **Populate database** — `DataInitializer` runs at startup as a `CommandLineRunner` and inserts 10 authors and 10 books if the tables are empty.
2. **Create** — `GET /books/new` and `GET /authors/new` render JSP forms; `POST /books` and `POST /authors` validate via `@Valid`, save through the service, and catch integrity violations to show a friendly error.
3. **Read** — `GET /books` calls `BookRepository.findAllBooksWithAuthors()`, a JPQL query that performs an **INNER JOIN** between `Book` and `Author` and returns a flat `BookAuthorView` projection. `GET /authors` lists authors using the default `findAll()`.
4. **Update** — `GET /books/{id}/edit` and `GET /authors/{id}/edit` pre-populate forms; `POST /books/{id}` and `POST /authors/{id}` apply the change through the service.

### Custom join query

```java
@Query("""
       SELECT new com.example.bookauthor.dto.BookAuthorView(
           b.id, b.title, b.isbn, b.genre, b.price,
           a.id, a.name, a.email, a.nationality)
       FROM Book b
       INNER JOIN b.author a
       ORDER BY a.name ASC, b.title ASC
       """)
List<BookAuthorView> findAllBooksWithAuthors();
```

---

## 2. How to run

```bash
mvn spring-boot:run
```

Then open <http://localhost:8080/>.

The H2 console is available at <http://localhost:8080/h2-console> with JDBC URL `jdbc:h2:mem:bookauthordb`, user `sa`, no password.

### Run tests

```bash
mvn test
```

Tests covered:

- `BookRepositoryTest` — `@DataJpaTest` slice testing the inner-join custom query and `existsByIsbn`.
- `AuthorServiceTest` — Mockito unit tests for save / update / findById / duplicate-email guard.
- `BookServiceTest` — Mockito unit tests for save / update / findById / duplicate-ISBN guard.

---

## 3. Challenges & how they were solved

| Challenge | Resolution |
|-----------|------------|
| Wiring JSP into a packaged Spring Boot 3.x app | Used `tomcat-embed-jasper` + `jakarta.servlet.jsp.jstl` (Jakarta namespace) and packaged as `war`; configured `spring.mvc.view.prefix/suffix` so controllers can return logical view names. |
| Reporting integrity violations cleanly to the user | Pre-checked uniqueness in the service (`existsByEmail`/`existsByIsbn`) and raised `DataIntegrityViolationException`; the controller catches it and re-renders the form with an `error` flash, while the global `@ControllerAdvice` handles any unexpected DB-level violation. |
| Avoiding the N+1 query when listing books | Replaced the default `findAll()` with a JPQL `INNER JOIN` projection into `BookAuthorView` so the list page issues a single query. |
| Binding the `Author` foreign key in the form | Used a separate `authorId` request parameter that the `BookService` resolves into an `Author` before saving — keeps the entity clean and lets the controller validate the selection. |

---

## 4. Project structure

```
book-author-app/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/bookauthor/
    │   │   ├── BookAuthorApplication.java
    │   │   ├── config/DataInitializer.java
    │   │   ├── controller/{AuthorController, BookController, HomeController, GlobalExceptionHandler}.java
    │   │   ├── dto/BookAuthorView.java
    │   │   ├── model/{Author, Book}.java
    │   │   ├── repository/{AuthorRepository, BookRepository}.java
    │   │   └── service/{AuthorService, BookService}.java
    │   ├── resources/
    │   │   ├── application.properties
    │   │   └── static/css/styles.css
    │   └── webapp/WEB-INF/views/
    │       ├── layout-header.jspf
    │       ├── layout-footer.jspf
    │       ├── book-list.jsp
    │       ├── book-form.jsp
    │       ├── author-list.jsp
    │       ├── author-form.jsp
    │       └── error.jsp
    └── test/java/com/example/bookauthor/
        ├── repository/BookRepositoryTest.java
        └── service/{AuthorServiceTest, BookServiceTest}.java
```

---

## 5. GitHub URL
```
https://github.com/divii2205/book-author-app
```
