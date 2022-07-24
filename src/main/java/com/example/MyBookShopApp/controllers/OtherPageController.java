package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OtherPageController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }


    @GetMapping("/faq")
    public String faqPage(){
        return "/faq";
    }

    @GetMapping("/contacts")
    public String contactsPage(){
        return "/contacts";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "/about";
    }

    @GetMapping("/documents")
    public String documentsPage(){
        return "/documents/index";
    }

    @GetMapping("/postponed")
    public String postponedPage(){
        return "/postponed";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "/search/index";
    }

    @GetMapping("/signin")
    public String signinPage(){
        return "/signin";
    }

    @GetMapping("/cart")
    public String cartPage(){
        return "/cart";
    }
}