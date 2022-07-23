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

//    private Integer pageNum=10;

//    @GetMapping("/books/recent")
//    public BooksPageDto recentsPage(@RequestParam(required = false, value = "date1") String date1,
//                                               @RequestParam(required = false, value = "date2") String date2,
//                                               @RequestParam(value = "offset", required = false) Integer offset,
//                                               @RequestParam(value = "limit", required = false) Integer limit,
//                                               @PageableDefault(sort = {"pub_date"}, direction = Sort.Direction.DESC) Pageable pageable,
//                                               Model model) throws ParseException {
//
//        if (date1==null){
//            date1="01.01.1900";
//        }
//        if (date2==null){
//            date2="30.12.2999";
//        }
//        if (offset==null){
//            offset=0;
//        }
//        if (limit==null){
//            limit=20;
//        }
//
////        return "/books/recent";
//        return new BooksPageDto((List<Book>) model.getAttribute("newBooks"));
//    }

//    @ModelAttribute("recentBookList")
//    public List<Book> recentBookListByDates(){
//        return bookService.getPageOfRecentBooksByDates(0,20);
//
//    }

//    @ModelAttribute("pubDatesDto")
//    public PubDatesDto pubDatesDto(){
//        return new PubDatesDto();
//    }

//    @ModelAttribute("resultList")
//    public List<Book> resultList(){
//        return new ArrayList<>();
//    }

//    @ModelAttribute("newBooks")
//    @ResponseBody
//    public List<Book> recentBooks(@RequestParam(required = false) List<Book> changedDateBooks,
//                                  @RequestParam(value = "limit", required = false) Integer limit,
//                                  @RequestParam(value = "offset", required = false) Integer offset,
//                                  @RequestParam(required = false, value = "date1") String date1,
//                                  @RequestParam(required = false, value = "date2") String date2,
//                                  Model model){
//        if (date1==null){
//            date1="01.01.1900";
//        }
//        if (date2==null){
//            date2="30.12.2999";
//        }
//        if (limit==null){
//            limit=20;
//        }
//        if (offset==null){
//            offset=0;
//        }else if (offset!=0){
//            limit+=10;
//        }
//        List<Book> resultList = bookService.getPageOfRecentBooksByDates(date1,date2,offset,limit);
//        model.addAttribute("newBooks",resultList);
//        return resultList;
//    }

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
        }else if (offset!=0){
            limit+=10;
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
        }else if (offset!=0){
            limit+=10;
        }
        BooksPageDto resultList = new BooksPageDto(bookService.getPageOfRecentBooksByDates(date1,date2,offset,limit));
        return resultList;
    }



}
