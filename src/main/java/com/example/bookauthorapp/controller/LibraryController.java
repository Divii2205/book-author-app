package com.example.bookauthorapp.controller;

import com.example.bookauthorapp.entity.Author;
import com.example.bookauthorapp.entity.Book;
import com.example.bookauthorapp.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // --- Read Operations ---

    @GetMapping("/")
    public String listEntities(Model model) {
        model.addAttribute("books", libraryService.getAllBooksWithAuthors());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "list";
    }

    // --- Create Operations ---

    @GetMapping("/add-author")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "add_author";
    }

    @PostMapping("/add-author")
    public String addAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add_author";
        }
        try {
            libraryService.saveAuthor(author);
            redirectAttributes.addFlashAttribute("successMessage", "Author added successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add_author";
        }
        return "redirect:/";
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "add_book";
    }

    @PostMapping("/add-book")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "add_book";
        }
        try {
            libraryService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "add_book";
        }
        return "redirect:/";
    }

    // --- Update Operation ---

    @GetMapping("/edit-book/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = libraryService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "edit_book";
    }

    @PostMapping("/edit-book/{id}")
    public String updateBook(@PathVariable("id") Long id, @Valid @ModelAttribute("book") Book book, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("authors", libraryService.getAllAuthors());
            book.setId(id);
            return "edit_book";
        }
        try {
            libraryService.updateBook(id, book);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "edit_book";
        }
        return "redirect:/";
    }
}
