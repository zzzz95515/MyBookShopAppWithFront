package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BookshopCartController {


    @ModelAttribute(name = "bookCart")
    public List<Book>BookCart(){
        return new ArrayList<>();
    }

    private final BookRepository bookRepository;

    public BookshopCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable(name = "slug") String slug, @CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model){

        if (cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents",String.join("/",cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            if (cookieBooks.size()>0){
                model.addAttribute("isCartEmpty",false);
            }
            else {
                model.addAttribute("isCartEmpty",true);
            }

        }
        else {
            model.addAttribute("isCartEmpty",true);
        }
        return "redirect:/books/cart";
    }


    @PostMapping("/changeBookStatus/cart/{slug}")
    public String handleChangeBookStatus(@PathVariable(name = "slug") String slug, @CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model){

        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
        return "redirect:/books/" + slug;
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(name = "cartContents", required = false) String cartContents, Model model){
        if (cartContents==null || cartContents.equals("")){
            model.addAttribute("isCartEmpty",true);
        } else {
            model.addAttribute("isCartEmpty",false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length()-1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");
            List<Book> booksFromCookiesSlugs = bookRepository.findBooksBySlugIn(cookiesSlugs);
            model.addAttribute("bookCart", booksFromCookiesSlugs);
        }
        return "cart";
    }













    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }
}
