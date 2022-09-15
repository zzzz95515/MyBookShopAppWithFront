package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.UsersCartRepository;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class BookshopCartControllerTest {
    private final UsersCartRepository cartRepository;
    private final BookstoreUserRegister userRegister;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookshopCartControllerTest(UsersCartRepository cartRepository, BookstoreUserRegister userRegister) {
        this.cartRepository = cartRepository;
        this.userRegister = userRegister;
    }

    @Test
    @WithUserDetails("zzzz95515@gmail.com")
    public void addBookToCartTest() throws Exception {
        mockMvc.perform(post("/books/changeBookStatus/cart/book-ilp-658"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails("zzzz95515@gmail.com")
    public void removeBookToCartTest() throws Exception {
        mockMvc.perform(post("/books/changeBookStatuscart/remove/book-ilp-658"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

}