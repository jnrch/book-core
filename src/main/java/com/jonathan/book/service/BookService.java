package com.jonathan.book.service;

import com.jonathan.book.exception.ResourceExistsException;
import com.jonathan.book.exception.ResourceNotFoundException;
import com.jonathan.book.model.Author;
import com.jonathan.book.model.Book;
import com.jonathan.book.respository.AuthorRepository;
import com.jonathan.book.respository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAllBooks() {
        logger.info("Getting all books");
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book with id: " + id + " doesn't exists");
        }
        logger.info("Getting book with id: " + id + " and Isbn: " + book.get().getIsbn());
        return book;
    }

    public Book createBook(Book book) {
        Boolean ExistsBookIsbn = bookRepository.existsByIsbn(book.getIsbn());

        if (ExistsBookIsbn) {
            throw new ResourceExistsException("Isbn: " + book.getIsbn() + " taken");
        }
        logger.info("Creating book with Isbn: " + book.getIsbn() + " and title: " + book.getTitle());
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        boolean existsBookById = bookRepository.existsById(id);

        if (!existsBookById) {
            throw new ResourceNotFoundException("Book with id: " + id + " doesn't exists");
        }
        book.setId(id);
        logger.info("Updating book with id: " + id);
        bookRepository.save(book);

        return book;
    }

    public void deleteBookById(Long id) {
        boolean exists = bookRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Book with id: " + id + " doesn't exists");
        }
        logger.info("Deleting book with id: " + id);
        bookRepository.deleteById(id);
    }

    public Book addBookAuthor(Long bookId, Long authorId) {
        Optional<Book> currentBook = bookRepository.findById(bookId);
        Optional<Author> currentAuthor = authorRepository.findById(authorId);

        if (currentBook.isEmpty()) {
            throw new ResourceNotFoundException("Book with id: " + bookId + " doesn't exists");
        }

        if (currentAuthor.isEmpty()) {
            throw new ResourceNotFoundException("Author with id: " + authorId + " doesn't exists");
        }

        currentBook.get().getAuthors().add(currentAuthor.get());

          return bookRepository.save(currentBook.get());
    }
}
