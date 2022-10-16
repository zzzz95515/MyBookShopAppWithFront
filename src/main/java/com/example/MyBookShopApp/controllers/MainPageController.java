package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.errs.EmptySearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookRepository bookRepository;
    @Autowired
    public MainPageController(BookRepository bookRepository, BookService bookService, TagService tagService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("recomendedBooks")
    public List<Book> recomendedBooks(){
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }
    @ModelAttribute("newBooks")
    public List<Book> newBooks(){
        return bookService.getPageOfNewBooks(0,6);
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getPageOfPopularBooks(0,6);
    }


    private final BookService bookService;

    private final TagService tagService;
    @GetMapping("/")
    public String mainPage(@CookieValue(name = "LastBooks", required = false) String cartContents, Model model){
        getLastViewed(cartContents,model);
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){

        return new BooksPageDto( bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }


    @GetMapping(value = {"/search","search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto, Model model) throws EmptySearchException {
        if (searchWordDto!=null){
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults", bookService.getPageOfGoogleBooksApiSearchResult(searchWordDto.getExample(),0,5));
            return "/search/index";
        }
        else {
            throw new EmptySearchException("searchWord is eampty");
        }
    }

    @GetMapping("search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto){
        return new BooksPageDto(bookService.getPageOfGoogleBooksApiSearchResult(searchWordDto.getExample(),offset,limit));
    }

    @GetMapping("/books/popular1")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){

        return new BooksPageDto( bookService.getPageOfPopularBooksOrderBy(offset, limit));
    }

    @GetMapping("/books/recent1")
    @ResponseBody
    public BooksPageDto getNewBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){

        return new BooksPageDto( bookService.getPageOfNewBooks(offset, limit));
    }

    @ModelAttribute("tags")
    public List<Tag> getTags(){
        return tagService.getTags();
    }


    public void getLastViewed(@CookieValue(name = "LastBooks", required = false) String cartContents, Model model){
        if (cartContents==null || cartContents.equals("")){
            model.addAttribute("isCartEmpty",true);
        } else {
            model.addAttribute("isCartEmpty",false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length()-1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");
            List<Book> booksFromCookiesSlugs = bookRepository.findBooksBySlugIn(cookiesSlugs);
            model.addAttribute("LastBooks", booksFromCookiesSlugs);
        }
    }

    @ModelAttribute("lastViewed")
    List<Book> lastBooks(){
        return new ArrayList<>();
    }
    @GetMapping("/viewed")
    public String recentBooksSearch(@CookieValue(name = "LastBooks", required = false) String cartContents, Model model){

        if (cartContents==null || cartContents.equals("")){
            model.addAttribute("isLastBooks",true);
        } else {
            model.addAttribute("isLastBooks",false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length()-1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");
            List<Book> booksFromCookiesSlugs = bookRepository.findBooksBySlugIn(cookiesSlugs);
            model.addAttribute("lastViewed", booksFromCookiesSlugs);
        }
        return "/books/last";
    }

}
