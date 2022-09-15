package com.example.MyBookShopApp.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTests {


    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBooksByAuthor_FirstName() {
        String token = "Whitney";
        List<Book> bookListByAuthor = bookRepository.findBooksByAuthor_FirstName(token);
        assertNotNull(bookListByAuthor);
        assertFalse(bookListByAuthor.isEmpty());
        for (Book book:bookListByAuthor){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getAuthor().getFirstName().contains(token));
        }
    }

    @Test
    void findBooksByTitleContaining() {
        String token = "King";
        List<Book> booksByTitle = bookRepository.findBooksByTitleContaining(token);
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
        for (Book book:booksByTitle){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getTitle().contains(token));
        }
    }

    @Test
    void getBestsellers() {
        List<Book> bestsellers = bookRepository.getBestsellers();
        assertNotNull(bestsellers);
        assertFalse(bestsellers.isEmpty());
        for (Book book:bestsellers){
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getIsBestseller()==1);
        }
    }
}