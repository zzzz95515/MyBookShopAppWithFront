package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class NewsPageController {


    private final BookService bookService;

    @Autowired
    public NewsPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("newBooks")
    public List<Book> newBooks(){
        return bookService.getNewBookData();
    }

    @GetMapping("/books/recent")
    public String newsPage(){
        return "/books/recent";
    }
}
