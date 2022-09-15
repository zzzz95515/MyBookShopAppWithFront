package com.example.MyBookShopApp.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookstoreUserRepositoryTests {

    private final BookstoreUserRepository bookstoreUserRepository;

    @Autowired
    public BookstoreUserRepositoryTests(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Test
    public void testAddNewUser(){
        BookstoreUser bookstoreUser = new BookstoreUser();
        bookstoreUser.setEmail("test1@mai.ru");
        bookstoreUser.setPhone("88005553535");
        bookstoreUser.setName("Tester");
        bookstoreUser.setPassword("1234567");
        assertNotNull(bookstoreUser);
        assertNotNull(bookstoreUserRepository.save(bookstoreUser));


    }

}