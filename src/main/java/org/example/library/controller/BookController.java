package org.example.library.controller;

import org.example.library.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** Provides REST endpoints for adding, viewing, updating, and deleting books. */
@RestController
@RequestMapping("/books")
public class BookController {
    private final List<Book> books = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if (book.getId() == null || findBook(book.getId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Book book = findBook(id);
        return book == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
        Book existingBook = findBook(id);
        if (existingBook == null) {
            return ResponseEntity.notFound().build();
        }

        // The ID comes from the URL; only the other book details are replaced.
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
        return ResponseEntity.ok(existingBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        Book book = findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        books.remove(book);
        return ResponseEntity.noContent().build();
    }

    private Book findBook(Integer id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
}
