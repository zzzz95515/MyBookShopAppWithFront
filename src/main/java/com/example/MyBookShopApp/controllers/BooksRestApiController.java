package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ApiResponse;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liquibase.pro.packaged.B;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<ApiResponse<Book>> booksByAuthor(@RequestParam("author") String authorName){
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = service.getBooksByAuthor(authorName);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-title")
    @ApiOperation("operation to get book of bookshop by title")
    public ResponseEntity<ApiResponse<Book>>  booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = service.getBooksByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
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

//    @GetMapping("books/bestsellers")
//    @ApiOperation("operation to get bestsellers of bookshop")
//    public ResponseEntity<List<Book>> bestsellers(){
//        return ResponseEntity.ok(service.getPopularBookData());
//    }


    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handlerMissingServletRequestParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST,"Missing reqiered parameters", exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handlerBookstoreApiWrongParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST,"Bad parameters value", exception), HttpStatus.BAD_REQUEST);
    }
}

