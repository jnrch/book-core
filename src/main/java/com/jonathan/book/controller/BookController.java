package com.jonathan.book.controller;

import com.jonathan.book.model.Book;
import com.jonathan.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/books")
@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

    @PostMapping("/{bookId}/author/{authorId}")
    public Book addBookAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return bookService.addBookAuthor(bookId, authorId);
    }
}
