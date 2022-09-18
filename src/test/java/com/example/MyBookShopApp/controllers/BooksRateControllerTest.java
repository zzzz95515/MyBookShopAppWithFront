package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BooksRateControllerTest {

    private final BookRateRepository bookRateRepository;
    private final BookReviewsRepository bookReviewsRepository;

    private final BookRateReviewService service;

    @Autowired
    private MockMvc mockMvc;
    private final BookRepository bookRepository;

    @Autowired
    BooksRateControllerTest(BookRateRepository bookRateRepository, BookReviewsRepository bookReviewsRepository, BookRateReviewService service, BookRepository bookRepository) {
        this.bookRateRepository = bookRateRepository;
        this.bookReviewsRepository = bookReviewsRepository;
        this.service = service;
        this.bookRepository = bookRepository;
    }

    @Test
    public void testRateBook(){
        BookRateEntity rate = new BookRateEntity();
        rate.setBook(bookRepository.findBookBySlug("book-ilp-658"));
        rate.setRateValue(5);
        assertNotNull(bookRateRepository.save(rate));

    }

    @Test
    public void testReviewBook() throws Exception {
        BookReviewsEnt rate = new BookReviewsEnt();
        rate.setBook(bookRepository.findBookBySlug("book-ilp-658"));
        rate.setReview("1234");
        rate.setPosRate(0);
        rate.setNegRate(0);
        rate.setDate(new Date());
        rate.setUsersName("NewUser");
        assertNotNull(bookReviewsRepository.save(rate));
    }

    @Test
    public void rateReviewTest(){
        BookReviewsEnt review = bookReviewsRepository.findById(1).get();
        BookReviewsEnt resp=service.rateBookReview(1,review);
        assertEquals("+1", resp);
        BookReviewsEnt resp2=service.rateBookReview(-1,review);
        assertEquals("-1",resp2);

    }
}