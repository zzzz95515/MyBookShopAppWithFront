package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final ResourceStorage storage;
    private final BookRepository bookRepository;

    public BooksController(ResourceStorage storage, BookRepository bookRepository) {
        this.storage = storage;
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("slugBook")
    @ResponseBody
    public Book getSlugBook(){
        return new Book();
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable(name = "slug", required = false) String slug, Model model){
        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("slugBook", book);

        return "/books/slug" ;
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file")MultipartFile file, @PathVariable(name = "slug", required = false) String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file,slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate);
        return ("redirect:/books/"+slug);
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
