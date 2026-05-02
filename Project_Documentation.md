# Book and Author Library Application

## Overview
This document outlines the approach, implementation details, and challenges faced while building the Spring Boot application for managing Books and Authors. The project implements Create, Read, and Update (CRU) operations using Spring Data JPA, Spring Web MVC with JSP, and an H2 in-memory database.

## Entity Relationship Design
The database consists of two primary entities: **Author** and **Book**.

- **Author**: Represents the writer of the book. It has a One-to-Many relationship with `Book`.
- **Book**: Represents a specific publication. It has a Many-to-One relationship with `Author`.

### Entity Attributes:
**Author**
- `id` (Primary Key, Auto-generated)
- `name` (String, Mandatory)
- `nationality` (String)

**Book**
- `id` (Primary Key, Auto-generated)
- `title` (String, Mandatory)
- `genre` (String)
- `publicationYear` (Integer)
- `author_id` (Foreign Key -> Author)

## Implementation Details

### Setup & Configuration
- **Database**: H2 In-Memory DB. Configured in `application.properties` to drop and create tables on startup (`create-drop`).
- **JSP Configuration**: Configured the view resolver for JSP files placed inside `WEB-INF/jsp/`.

### Populate Database (Data Initialization)
Used a `CommandLineRunner` (`DataInitializer.java`) to insert 10 sample Authors and 10 sample Books on application startup.
```java
List<Author> authors = Arrays.asList(
    new Author("J.K. Rowling", "British"),
    // ... 9 more authors
);
authorRepository.saveAll(authors);

List<Book> books = Arrays.asList(
    new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 1997, savedAuthors.get(0)),
    // ... 9 more books
);
bookRepository.saveAll(books);
```

### Read Operation & Custom Inner Join Query
Implemented a view (`list.jsp`) to display all entities.
Created a custom `@Query` in `BookRepository` to fetch books with their authors using an inner join:
```java
@Query("SELECT b FROM Book b JOIN FETCH b.author")
List<Book> findAllBooksWithAuthors();
```
The Controller maps `/` to `listEntities`, retrieving all records and returning the `list.jsp` view.

### Create Operation
Implemented two separate forms: `add_author.jsp` and `add_book.jsp`.
The `LibraryController` provides `GET` mapping for showing the forms and `POST` mapping for processing submissions. Validation is handled using `@Valid`, and `BindingResult` ensures proper flow back to the form if validation fails.

**Exception Handling for Integrity Violations**:
In `LibraryService.java`, a `try-catch` block catches `DataIntegrityViolationException` when attempting to save an entity with invalid or conflicting database constraints, wrapping it in a user-friendly runtime exception message which is then displayed in the JSP.
```java
@Transactional
public Book saveBook(Book book) {
    try {
        return bookRepository.save(book);
    } catch (DataIntegrityViolationException e) {
        throw new RuntimeException("Integrity violation: Could not save book. Please ensure the author exists.", e);
    }
}
```

### Update Operation
Implemented an `edit_book.jsp` form. The controller method maps to `/edit-book/{id}`.
It fetches the existing book via the Service layer and populates the model. Upon submission, the Service layer finds the existing entity, applies the new values, and saves it back to the database.

### Testing
Implemented Unit Tests using JUnit 5 and Mockito.
- `LibraryServiceTest`: Mocks the repositories and tests success/failure paths for saving and updating entities, explicitly testing the Exception handling.
- `BookRepositoryTest`: Uses `@DataJpaTest` to verify that the custom Inner Join query successfully retrieves the linked Author object.

## Challenges Faced
1. **JSP Integration in Spring Boot**: Modern Spring Boot favors Thymeleaf or React/Angular. Integrating JSP required ensuring `tomcat-embed-jasper` and JSTL dependencies were present, and correctly placing the JSP files inside `src/main/webapp/WEB-INF/jsp/`.
2. **Entity Detachment on Update**: When updating a book via JSP, the submitted `Book` object is a detached entity. It's crucial to map the new values to the existing managed entity inside a `@Transactional` service method rather than simply calling `.save()` directly on the detached object, to avoid overriding omitted fields or losing tracking.
3. **Data Integrity Violation Simulation**: H2 doesn't always strictly throw `DataIntegrityViolationException` for simple missing validations (since `@NotNull` is checked at the JPA layer first). Added `@NotBlank` annotations and manual DB-level checks to accurately capture and handle these specific exceptions.

## Github URL
*(Insert your GitHub repository URL here)*
