package com.jonathan.book.service;

import com.jonathan.book.exception.ResourceNotFoundException;
import com.jonathan.book.model.Book;
import com.jonathan.book.respository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new ResourceNotFoundException("Book with id: " + id + " doesn't exists");
        }
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        Optional<Book> currentBook = bookRepository.findById(id);

        if (currentBook.isEmpty()) {
            throw new ResourceNotFoundException("Book with id: " + id + " doesn't exists");
        }

        book.setId(currentBook.get().getId());
        bookRepository.save(book);

        return book;
    }

    public void deleteBookById(Long id) {
        boolean exists = bookRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Book with id: " + id + " doesn't exists");
        }
        bookRepository.deleteById(id);
    }
}
