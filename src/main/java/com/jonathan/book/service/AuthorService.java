package com.jonathan.book.service;

import com.jonathan.book.exception.ResourceNotFoundException;
import com.jonathan.book.model.Author;
import com.jonathan.book.respository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AuthorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAllAuthors() {
        logger.info("Getting all authors");
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isEmpty()) {
            throw new ResourceNotFoundException("Author with id: " + id + " doesn't exists");
        }
        logger.info("Getting author with id: " + id + " and name: " + authorOptional.get().getFirstName() + " " + authorOptional.get().getLastName());
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        logger.info("Creating author with name: " + author.getFirstName() + " " + author.getLastName());
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author author) {
        Optional<Author> currentAuthor = authorRepository.findById(id);

        if (currentAuthor.isEmpty()) {
            throw new ResourceNotFoundException("Author with id: " + id + " doesn't exists");
        }

        author.setId(currentAuthor.get().getId());
        logger.info("Updating author with id: " + id);
        authorRepository.save(author);

        return author;
     }

     public void deleteAuthor(Long id) {
       logger.info("Deleting author with id: " + id);
       authorRepository.deleteById(id);
     }

}
