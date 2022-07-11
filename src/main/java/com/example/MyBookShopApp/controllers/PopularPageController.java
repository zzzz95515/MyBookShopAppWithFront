package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PopularPageController {
    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getPopularBookData();
    }
    private final BookService bookService;

    public PopularPageController(BookService bookService) {
        this.bookService = bookService;
    }


}
