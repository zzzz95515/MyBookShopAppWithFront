package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recomendedBooks")
    public List<Book> recomendedBooks(){
        return bookService.getBooksData();
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
//        model.addAttribute("bookData", bookService.getBooksData());
//        model.addAttribute("searchPlaceholder", "new search placeholder");
//        model.addAttribute("serverTime", new SimpleDateFormat("hh:mm:ss").format(new Date()));
//        model.addAttribute("placeholderTextPart2","SERVER");
//        model.addAttribute("messageTemplate","searchbar.placeholder2");
        return "index";
    }

//    @GetMapping("/genres")
//    public String genresPage(){
//        return "genres/index";
//    }

//    @GetMapping("/books/authors")
//    public String authorsPage(Model model){
//        model.addAttribute("author", bookService.getAuthors());
//        return "authors/index";
//    }
//    @GetMapping("/books/popular.html")
//    public String popularPage(){
//        return "books/popular";
//    }
}
