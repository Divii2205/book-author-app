package com.example.bookauthor.controller;

import com.example.bookauthor.model.Book;
import com.example.bookauthor.service.AuthorService;
import com.example.bookauthor.service.BookService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAllWithAuthors());
        return "book-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") Book book,
                         BindingResult bindingResult,
                         @RequestParam(value = "authorId", required = false) Long authorId,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (authorId == null) {
            bindingResult.rejectValue("author", "NotNull", "Please select an author");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "book-form";
        }
        try {
            bookService.save(book, authorId);
            redirectAttributes.addFlashAttribute("success", "Book created successfully.");
            return "redirect:/books";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            return "book-form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("book") Book book,
                         BindingResult bindingResult,
                         @RequestParam(value = "authorId", required = false) Long authorId,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (authorId == null) {
            bindingResult.rejectValue("author", "NotNull", "Please select an author");
        }
        if (bindingResult.hasErrors()) {
            book.setId(id);
            model.addAttribute("authors", authorService.findAll());
            return "book-form";
        }
        try {
            bookService.update(id, book, authorId);
            redirectAttributes.addFlashAttribute("success", "Book updated successfully.");
            return "redirect:/books";
        } catch (DataIntegrityViolationException ex) {
            book.setId(id);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            return "book-form";
        }
    }
}
