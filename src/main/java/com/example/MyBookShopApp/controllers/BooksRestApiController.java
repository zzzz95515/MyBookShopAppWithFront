package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liquibase.pro.packaged.B;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BooksRestApiController {

    private final BookService service;

    public BooksRestApiController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book of bookshop by author passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName){
        return ResponseEntity.ok(service.getBooksByAuthor(authorName));
    }

    @GetMapping("/books/by-title")
    @ApiOperation("operation to get book of bookshop by title")
    public ResponseEntity<List<Book>> booksByTitle(@RequestParam("title") String title){
        return ResponseEntity.ok(service.getBooksByTitle(title));
    }

    @GetMapping("/books/by-price")
    @ApiOperation("operation to get book of bookshop with price")
    public ResponseEntity<List<Book>> booksByPrice(@RequestParam("price") Double price){
        return ResponseEntity.ok(service.getBooksWithPrice(price));
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("operation to get book of bookshop with price between min and max")
    public ResponseEntity<List<Book>> booksByPriceBetween(@RequestParam("min") Double min, @RequestParam("max") Double max){
        return ResponseEntity.ok(service.getBooksWithPriceBetween(min,max));
    }

    @GetMapping("/books/max-discount")
    @ApiOperation("operation to get books of bookshop with max discount")
    public ResponseEntity<List<Book>> booksWithMaxDiscount(){
        return ResponseEntity.ok(service.getBooksWithMaxDiscount());
    }

    @GetMapping("books/bestsellers")
    @ApiOperation("operation to get bestsellers of bookshop")
    public ResponseEntity<List<Book>> bestsellers(){
        return ResponseEntity.ok(service.getPopularBookData());
    }



}

