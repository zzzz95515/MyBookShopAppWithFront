package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRateEntity;
import com.example.MyBookShopApp.data.BookRateRepository;
import com.example.MyBookShopApp.data.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksRateController {

    private final BookRepository repository;
    private final BookRateRepository rateRepository;


    public BooksRateController(BookRepository repository, BookRateRepository rateRepository) {
        this.repository = repository;
        this.rateRepository = rateRepository;
    }





    @PostMapping("/rateBook")
    public String rateBook(@RequestParam("bookId") String bookSlug, @RequestParam("value")Integer value){
        Book book = repository.findBookBySlug(bookSlug);
//        rateRepository.save(new BookRateEntity(value,book));
        return ("redirect:/books/"+bookSlug);
    }
}
