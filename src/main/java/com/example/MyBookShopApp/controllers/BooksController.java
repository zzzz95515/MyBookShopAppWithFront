package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import javassist.bytecode.ByteArray;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookRepository bookRepository;
    private final ResourceStorage storage;

    public BooksController(BookRepository bookRepository, ResourceStorage storage) {
        this.bookRepository = bookRepository;
        this.storage = storage;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model){
        Book bookBySlug= bookRepository.findBookBySlug(slug);
        model.addAttribute("bookBySlug",bookBySlug);
        return "/books/slug";
    }




    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImg(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file,slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate);

        return ("redirect:/books/"+slug);
    }


    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book path is: "+ path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book mime is: "+ mediaType);

        byte[] data = storage.getBookFileArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data length is: "+ data.length);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + path
                .getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

}
