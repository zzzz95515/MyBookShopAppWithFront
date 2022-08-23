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
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {


    private final BookRepository bookRepository;
    private final ResourceStorage storage;

    private final BookRateRepository bookRateRepository;

    public BooksController(BookRepository bookRepository, ResourceStorage storage, BookRateRepository bookRateRepository) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.bookRateRepository = bookRateRepository;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model){
        Book bookBySlug= bookRepository.findBookBySlug(slug);
        model.addAttribute("bookBySlug",bookBySlug);
        List<BookRateEntity> ratesList=bookRateRepository.findAllByBook_Slug(slug);
        Integer totalNum=ratesList.size();
        Integer rate1=0;
        Integer rate2=0;
        Integer rate3=0;
        Integer rate4=0;
        Integer rate5=0;
        for (BookRateEntity rates: ratesList){
            switch (rates.getRateValue()){
                case (1):
                    rate1++;
                    break;
                case (2):
                    rate2++;
                    break;
                case (3):
                    rate3++;
                    break;
                case (4):
                    rate4++;
                    break;
                case (5):
                    rate5++;
                    break;
                default:
                    rate5++;
                    break;
            }
        }
        Double totalRate= Double.valueOf((rate1+2*rate2+rate3*3+rate4*4+rate5*5)/totalNum);
        model.addAttribute("bookRate1",rate1);
        model.addAttribute("bookRate2",rate2);
        model.addAttribute("bookRate3",rate3);
        model.addAttribute("bookRate4",rate4);
        model.addAttribute("bookRate5",rate5);
        model.addAttribute("totalRate",totalRate);
        model.addAttribute("totalNum",totalNum);
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
