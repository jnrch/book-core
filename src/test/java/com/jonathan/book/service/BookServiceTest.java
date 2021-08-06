package com.jonathan.book.service;

import com.jonathan.book.exception.ResourceExistsException;
import com.jonathan.book.exception.ResourceNotFoundException;
import com.jonathan.book.model.Author;
import com.jonathan.book.model.Book;
import com.jonathan.book.respository.AuthorRepository;
import com.jonathan.book.respository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {BookService.class})
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    private BookService underTest;

    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookRepository, authorRepository);
        book = new Book(1L,"1-7320000-1-3", "Mapas mentales", 16, new Date());
        author = new Author(1L,"Karl","Klein", new Date());
    }

    @Test
    void canGetAllBooks() {
        //when
        underTest.findAllBooks();
        //then
        verify(bookRepository).findAll();
    }

    @Test
    void canGetBookById() {
        Long id = 1L;

        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        Optional<Book> expected = underTest.findBookById(id);

        assertThat(expected).isNotNull();
    }

    @Test
    void canCreateABook() {
        //given
        //when
        underTest.createBook(book);
        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book captureBook = bookArgumentCaptor.getValue();

        assertThat(captureBook).isEqualTo(book);
    }

    @Test
    void willThrowWhenIsbnIsTaken() {
        //given
        given(bookRepository.existsByIsbn(book.getIsbn())).willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> underTest.createBook(book))
                .isInstanceOf(ResourceExistsException.class)
                .hasMessageContaining("Isbn: " + book.getIsbn() + " taken");

        verify(bookRepository, never()).save(any());
    }

    @Test
    void willThrowExceptionWhenBookIsEmpty() {
        //given
        Long bookId = 1L;
        given(bookRepository.findById(bookId)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.findBookById(bookId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book with id: " + bookId + " doesn't exists");
    }

    @Test
    void canUpdateABook() {
        //given
        long id = 1;
        given(bookRepository.existsById(id)).willReturn(true);

        underTest.updateBook(id, book);
        verify(bookRepository).save(book);
    }

    @Test
    void deleteBookById() {
        long id = 1;
        //given
        given(bookRepository.existsById(id)).willReturn(true);
        //when
        underTest.deleteBookById(id);
        //then
        verify(bookRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteBookNotFound() {
        //given
        long id = 1;
        given(bookRepository.existsById(id)).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> underTest.deleteBookById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book with id: " + id + " doesn't exists");

        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void willThrowWhenUpdateBookIdIsNotFound() {
        //given
        long id = 1;
        given(bookRepository.existsById(id)).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> underTest.updateBook(id, book))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book with id: " + id + " doesn't exists");
        verify(bookRepository, never()).save(any());
    }

    @Test
    @Disabled
    void addBookAuthor() {
        //given
        long bookId = 1;
        long authorId = 1;
        Book book1 = new Book(1L,"1-7320000-1-3", "Mapas mentales", 16, new Date(), Collections.singleton(author));

        Optional<Book> currentBook = bookRepository.findById(bookId);
        Optional<Author> currentAuthor = authorRepository.findById(authorId);
        given(bookRepository.findById(bookId)).willReturn(Optional.of(book1));
        given(authorRepository.findById(authorId)).willReturn(Optional.of(author));

        underTest.addBookAuthor(bookId, authorId);
        //currentBook.get().getAuthors().add(currentAuthor.get());

        verify(bookRepository).save(book1);
    }
}