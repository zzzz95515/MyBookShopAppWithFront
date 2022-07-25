package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@Api(description = "authors data")
public class AuthorsController {


    private final AuthorsService authorsService;
    private final BookService bookService;

    public AuthorsController(AuthorsService authorsService, BookService bookService) {
        this.authorsService = authorsService;
        this.bookService = bookService;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        Map<String,List<Author>> authorsMap = authorsService.getAuthorsMap();
        return authorsMap;
    }

    @GetMapping(value = {"authors/{authorId}"})
    public String getTagResults(@PathVariable(value = "authorId", required = false) Integer authorId, Model model){
        Author author=authorsService.getAuthorById(authorId);
        if (author.getDescription()==null){
            author.setDescription("В разработке");
        }
        if (author.getPhoto()==null){
            author.setPhoto("http://dummyimage.com/768x513.png/dddddd/000000");
        }
        model.addAttribute("authorsBook", bookService.getPageOfAuthorsBooks(authorId,0,6));
        model.addAttribute("authorr",author);
        return "/authors/slug";
    }




    @GetMapping("/books/author/{authorId}")
    @ResponseBody
    public String getNextSearchPage(@PathVariable(value = "authorId", required = false) Integer authorId,
                                          @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit,Model model){
//        if (limit==null){
//            limit=20;
//        }
//        if (offset==null){
//            offset=0;
//        }
//        model.addAttribute("authorsBook",bookService.getPageOfAuthorsBooks(authorId,offset,limit));
        return "/books/author";
    }

    @GetMapping("authors")
    public String authorsPage(){
        Logger.getLogger("authors").info("Зашел в контроллер авторов");
        return "/authors/index";
    }















    @ApiOperation("method to get Map of authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String,List<Author>> authors(){
        return authorsService.getAuthorsMap();
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
