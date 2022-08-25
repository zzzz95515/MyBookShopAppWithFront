package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class PostponedController {

    @ModelAttribute(name = "postponedBooks")
    public List<Book> postponedBooks(){
        return new ArrayList<>();
    }

    private final BookRepository repository;

    public PostponedController(BookRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/changeBookStatus/kept/{slug}")
    public String handleChangeBookStatus(@PathVariable(name = "slug") String slug, @CookieValue(name = "keptContents", required = false) String keptContents, HttpServletResponse response, Model model){

        if (keptContents == null || keptContents.equals("")) {
            Cookie cookie = new Cookie("keptContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);

            model.addAttribute("isKeptEmpty", false);
        } else if (!keptContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(keptContents).add(slug);
            Cookie cookie = new Cookie("keptContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isKeptEmpty", false);
        }
        return "redirect:/books/" + slug;
    }

    @PostMapping("/changeBookStatus/kept/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable(name = "slug") String slug,
                                                  @CookieValue(name = "keptContents", required = false) String cartContents,
                                                  HttpServletResponse response, Model model){

        if (cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("keptContents",String.join("/",cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            if (cookieBooks.size()>0){
                model.addAttribute("isKeptEmpty",false);
            }
            else {
                model.addAttribute("isKeptEmpty",true);
            }

        }
        else {
            model.addAttribute("isKeptEmpty",true);
        }
        return "redirect:/books/postponed";
    }



    @GetMapping("/postponed")
    public String handleCartRequest(@CookieValue(name = "keptContents", required = false) String cartContents, Model model){
        if (cartContents==null || cartContents.equals("")){
            model.addAttribute("isKeptEmpty",true);
        } else {
            model.addAttribute("isKeptEmpty",false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length()-1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");
            List<Book> booksFromCookiesSlugs = repository.findBooksBySlugIn(cookiesSlugs);
            model.addAttribute("postponedBooks", booksFromCookiesSlugs);
        }
        return "postponed";
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
