package com.jonathan.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jonathan.book.model.Book;
import com.jonathan.book.respository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    private final Faker faker = new Faker();

    /*@Test
    @Disabled
    void findAllBooks() {
    }

    @Test
    @Disabled
    void findBookById() {
    }*/

    @Test
    void canRegisterNewBook() throws Exception {
        //given
        Book book = new Book("1-7320000-1-3", "Mapas mentales", 16, new Date());

        //when
        ResultActions resultActions = mockMvc
                .perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)));
        System.out.println(resultActions);
        //then
        resultActions.andExpect(status().isOk());
    }
}