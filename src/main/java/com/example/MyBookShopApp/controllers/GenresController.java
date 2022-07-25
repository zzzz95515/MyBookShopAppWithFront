package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GenresController {

    private final BookService bookService;
    private final GenreService genreService;


    @ModelAttribute(name = "allGenres")
    public List<Genre> allGenres(){
        return genreService.getGenres();
    }

    public GenresController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @ModelAttribute(name = "genreResults")
    public List<Book> genreResults(){
        return new ArrayList<>();
    }

    @GetMapping("/genres")
    public String genresPage(){
        return "genres/index";
    }

    @GetMapping(value = {"genres/{genreWord}"})
    public String getTagResults(@PathVariable(value = "genreWord", required = false) String genreWord, Model model){
        model.addAttribute("genreResults", bookService.getPageOfBooksByGenre(genreWord,0,20));
        model.addAttribute("genreWord",genreWord);
        return "/genres/slug";
    }

    @GetMapping("/books/genre/{genreWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@PathVariable(value = "genreWord", required = false) String genreWord,
                                          @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfBooksByGenre(genreWord,offset,limit));
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
