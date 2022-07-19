package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.AuthorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@Api(description = "authors data")
public class AuthorsController {


    private final AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        Map<String,List<Author>> authorsMap = authorsService.getAuthorsMap();
        return authorsMap;
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
}
