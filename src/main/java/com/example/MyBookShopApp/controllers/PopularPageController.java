package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class PopularPageController {


    private final BookService bookService;

    @Autowired
    public PopularPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/popular")
    public String popularPage(){
        return "/books/popular";
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

//    @ModelAttribute("popularBooks")
//    public List<Book> popularBooks(){
//        return bookService.getPopularBookData();
//    }


    @ModelAttribute("popularBooks")
    public String recentBooksSearch(@RequestParam(value = "limit", required = false) Integer limit,
                                    @RequestParam(value = "offset", required = false) Integer offset,
                                    Model model){

        if (limit==null){
            limit=20;
        }
        if (offset==null){
            offset=0;
        }
        List<Book> resultList = bookService.getPageOfPopularBooksOrderBy(offset,limit);
        model.addAttribute("popularBooks",resultList);
        return "/books/popular";
    }


    @GetMapping("/books/popular2")
    @ResponseBody
    public BooksPageDto recentBooks(@RequestParam(value = "limit", required = false) Integer limit,
                                    @RequestParam(value = "offset", required = false) Integer offset,
                                    Model model){
        if (limit==null){
            limit=20;
        }
        if (offset==null){
            offset=0;
        }
        BooksPageDto resultList = new BooksPageDto(bookService.getPageOfPopularBooksOrderBy(offset,limit));
        Logger.getLogger(NewsPageController.class.getName()).info("вывод");
        return resultList;
    }
}
