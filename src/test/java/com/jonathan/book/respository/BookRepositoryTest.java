package com.jonathan.book.respository;

import com.jonathan.book.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfBookByIsbnExists() {
        //given
        String isbn = "1-7320000-1-3";
        Book book = new Book(isbn,
                "Mapas mentales",
                3500,
                new Date()
        );
        underTest.save(book);
        //when
        boolean bookExists = underTest.existsByIsbn(isbn);
        //then
        assertThat(bookExists).isTrue();
    }

    @Test
    void itShouldCheckIfBookByIsbnDoesNotExists() {
        //given
        String isbn = "1-7320000-1-3";
        //when
        boolean bookExists = underTest.existsByIsbn(isbn);
        //then
        assertThat(bookExists).isFalse();
    }
}