package com.example.bookauthor.service;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Sample", "sample@test.com", "Indian");
    }

    @Test
    void save_persistsWhenEmailIsUnique() {
        when(authorRepository.existsByEmail("sample@test.com")).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author saved = authorService.save(author);

        assertThat(saved.getName()).isEqualTo("Sample");
        verify(authorRepository).save(author);
    }

    @Test
    void save_throwsWhenEmailDuplicate() {
        when(authorRepository.existsByEmail("sample@test.com")).thenReturn(true);

        assertThatThrownBy(() -> authorService.save(author))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("already exists");

        verify(authorRepository, never()).save(any());
    }

    @Test
    void findById_throwsWhenMissing() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Author not found");
    }

    @Test
    void update_overwritesFieldsAndSaves() {
        Author existing = new Author("Old", "old@test.com", "X");
        existing.setId(7L);
        when(authorRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author updated = new Author("New Name", "new@test.com", "Y");
        Author result = authorService.update(7L, updated);

        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getEmail()).isEqualTo("new@test.com");
        assertThat(result.getNationality()).isEqualTo("Y");
    }
}
