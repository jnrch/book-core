package com.jonathan.book.controller;

import com.jonathan.book.model.Publisher;
import com.jonathan.book.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/publishers")
@RestController
public class PublisherController {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> findAllPublisher() {
        return publisherService.findAllPublishers();
    }

    @GetMapping("/{id}")
    public Optional<Publisher> findPublisherById(@PathVariable Long id) {
        return publisherService.findPublisherById(id);
    }

    @GetMapping("/book/{bookId}")
    public Page<Publisher> findAllPublisherByBookId(@PathVariable Long bookId, Pageable pageable) {
        return publisherService.findPublisherByBookId(bookId, pageable);
    }

    @PostMapping("/book/{bookId}")
    public Publisher createPublisher(@PathVariable Long bookId, @RequestBody Publisher publisher) {
        return publisherService.savePublisher(bookId, publisher);
    }
}
