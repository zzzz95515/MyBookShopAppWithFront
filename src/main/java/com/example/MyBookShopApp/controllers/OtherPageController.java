package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherPageController {
    @GetMapping("/books/recent")
    public String newsPage(){
        return "/books/recent";
    }

    @GetMapping("/books/popular")
    public String popularPage(){
        return "/books/popular";
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