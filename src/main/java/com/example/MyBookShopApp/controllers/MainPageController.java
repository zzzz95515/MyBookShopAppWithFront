package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.RecommendedBooksPageDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recomendedBooks")
    public List<Book> recomendedBooks(){
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }
    @ModelAttribute("newBooks")
    public List<Book> newBooks(){
        return bookService.getNewBookData();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getPopularBookData();
    }

    private final BookService bookService;
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping("/books/recommended")
    public RecommendedBooksPageDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){

        return new RecommendedBooksPageDto( bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }
}
