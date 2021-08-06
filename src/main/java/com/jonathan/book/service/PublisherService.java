package com.jonathan.book.service;

import com.jonathan.book.exception.ResourceNotFoundException;
import com.jonathan.book.model.Book;
import com.jonathan.book.model.Publisher;
import com.jonathan.book.respository.BookRepository;
import com.jonathan.book.respository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;
    private BookRepository bookRepository;

    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> findPublisherById(Long id) {
        Optional<Publisher> currentPublisher = publisherRepository.findById(id);

        if (currentPublisher.isEmpty()) {
            throw new ResourceNotFoundException("Publisher with id: " + id + " doesn't exists");
        }

        return publisherRepository.findById(id);
    }

    public Page<Publisher> findPublisherByBookId(Long bookId, Pageable pageable) {
        return publisherRepository.findByBookId(bookId, pageable);
    }

    public Publisher savePublisher(Long bookId, Publisher publisher) {
        Optional<Book> currentBook = bookRepository.findById(bookId);

        if (currentBook.isEmpty()) {
            throw new ResourceNotFoundException("Book with id: " + bookId + "doesn't exists");
        }

        publisher.setBook(currentBook.get());

        return publisherRepository.save(publisher);
    }

    @Transactional
    public Publisher updatePublisher(Long id, Publisher publisher) {
        Optional<Publisher> currentPublisher = publisherRepository.findById(id);

        if (currentPublisher.isEmpty()) {
            throw new ResourceNotFoundException("Publisher with id: " + id + " doesn't exists");
        }

        publisher.setId(currentPublisher.get().getId());
        publisherRepository.save(publisher);

        return publisher;
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
