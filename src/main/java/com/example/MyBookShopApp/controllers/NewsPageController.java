package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class NewsPageController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }
    private final BookService bookService;

    @Autowired
    public NewsPageController(BookService bookService) {
        this.bookService = bookService;
    }

//    @ModelAttribute("newBooks")
//    public List<Book> newBooks(){
//        return bookService.getNewBookData();
//    }

    @GetMapping("/books/recent")
    public String newsPage(){
        return "/books/recent";
    }


    @ModelAttribute("newBooks")
    public String recentBooksSearch(@RequestParam(required = false) List<Book> changedDateBooks,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    @RequestParam(value = "offset", required = false) Integer offset,
                                    @RequestParam(required = false, value = "date1") String date1,
                                    @RequestParam(required = false, value = "date2") String date2,
                                    Model model){
        if (date1==null){
            date1="01.01.1900";
        }
        if (date2==null){
            date2="30.12.2999";
        }
        if (limit==null){
            limit=20;
        }
        if (offset==null){
            offset=0;
        }
        List<Book> resultList = bookService.getPageOfRecentBooksByDates(date1,date2,offset,limit);
        model.addAttribute("newBooks",resultList);
        return "/books/recent";
    }


    @GetMapping("/books/recent2")
    @ResponseBody
    public BooksPageDto recentBooks(@RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "offset", required = false) Integer offset,
                                  @RequestParam(required = false, value = "date1") String date1,
                                  @RequestParam(required = false, value = "date2") String date2,
                                  Model model){
        if (date1==null){
            date1="01.01.1900";
        }
        if (date2==null){
            date2="30.12.2999";
        }
        if (limit==null){
            limit=20;
        }
        if (offset==null){
            offset=0;
        }
        BooksPageDto resultList = new BooksPageDto(bookService.getPageOfRecentBooksByDates(date1,date2,offset,limit));
        Logger.getLogger(NewsPageController.class.getName()).info("вывод");
        return resultList;
    }



}
