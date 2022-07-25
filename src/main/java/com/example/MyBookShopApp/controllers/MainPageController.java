package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
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
    public String mainPage(){
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
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto, Model model){
        model.addAttribute("searchWordDto", searchWordDto);
        model.addAttribute("searchResults", bookService.getPageOfSearchResultBooks(searchWordDto.getExample(),0,5).getContent());
        return "/search/index";
    }

    @GetMapping("search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto){
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(),offset,limit).getContent());
    }

    @GetMapping("/books/popular1")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){

        return new BooksPageDto( bookService.getPageOfPopularBooks(offset, limit));
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

}
