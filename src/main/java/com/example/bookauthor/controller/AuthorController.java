package com.example.bookauthor.controller;

import com.example.bookauthor.model.Author;
import com.example.bookauthor.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "author-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("author", new Author());
        return "author-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("author") Author author,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "author-form";
        }
        try {
            authorService.save(author);
            redirectAttributes.addFlashAttribute("success", "Author created successfully.");
            return "redirect:/authors";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", ex.getMessage());
            return "author-form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "author-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("author") Author author,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            author.setId(id);
            return "author-form";
        }
        try {
            authorService.update(id, author);
            redirectAttributes.addFlashAttribute("success", "Author updated successfully.");
            return "redirect:/authors";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", ex.getMessage());
            author.setId(id);
            return "author-form";
        }
    }
}
