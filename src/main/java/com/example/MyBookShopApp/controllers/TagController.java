package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TagController {

    private final TagService tagService;
    private final BookService bookService;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    public TagController(TagService tagService, BookService bookService) {
        this.tagService = tagService;
        this.bookService = bookService;
    }



    @ModelAttribute(name = "tagResults")
    public List<Book> tagResults(){
        return new ArrayList<>();
    }

    public String tagWordd;

    @GetMapping(value = {"tags/{tagWord}"})
    public String getTagResults(@PathVariable(value = "tagWord", required = false) String tagWord, Model model){
        model.addAttribute("tagResults", bookService.getPageOfBooksByTag(tagWord,0,20));
        model.addAttribute("tagWord",tagWord);
        return "/tags/index";
    }

    @GetMapping("/books/tag/{tagWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@PathVariable(value = "tagWord", required = false) String tagWord,
                                          @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfBooksByTag(tagWord,offset,limit));
    }
}
