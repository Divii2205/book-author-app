package com.example.bookauthor.service;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.repository.AuthorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author not found: " + id));
    }

    @Transactional
    public Author save(Author author) {
        if (author.getId() == null && authorRepository.existsByEmail(author.getEmail())) {
            throw new DataIntegrityViolationException(
                    "An author with email '" + author.getEmail() + "' already exists.");
        }
        return authorRepository.save(author);
    }

    @Transactional
    public Author update(Long id, Author updated) {
        Author existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setNationality(updated.getNationality());
        return authorRepository.save(existing);
    }
}
