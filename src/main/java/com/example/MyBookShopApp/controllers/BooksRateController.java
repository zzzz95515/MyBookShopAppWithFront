package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class BooksRateController {

    private final BookRepository repository;
    private final BookRateRepository rateRepository;

    private final BookReviewsRepository reviewsRepository;

    private final BookstoreUserRegister userRegister;

    private final BookRateReviewService service;


    public BooksRateController(BookRepository repository, BookRateRepository rateRepository, BookReviewsRepository reviewsRepository, BookstoreUserRegister userRegister, BookRateReviewService service) {
        this.repository = repository;
        this.rateRepository = rateRepository;
        this.reviewsRepository = reviewsRepository;
        this.userRegister = userRegister;
        this.service = service;
    }

    @PostMapping("/rateBook")
    public String rateBook(@RequestBody RateBookResponce bookResponce, Model model){
        if (userRegister.getCurrentUser()!=null){
            service.saveBookRate(bookResponce);
        }
        else {
            model.addAttribute("AuthFaildForRate",true);
            throw new UsernameNotFoundException("зарегистрируйтесь, чтобы оставить оценку");
        }
        return ("redirect:/books/"+bookResponce.getBookId());
    }

    @PostMapping("/bookReview")
    public String reviewBook(@RequestBody ReviewBookResponce bookResponce){
        BookstoreUser bookstoreUser= (BookstoreUser) userRegister.getCurrentUser();
        if (bookstoreUser!=null){
            service.saveBookReview(bookResponce,bookstoreUser);
        }

        return ("redirect:/books/"+bookResponce.getBookId());
    }

    @PostMapping("/rateBookReview")
    public String rateReview(@RequestBody RateReview rateBookReview){
        BookstoreUser bookstoreUser= (BookstoreUser) userRegister.getCurrentUser();
        BookReviewsEnt review = reviewsRepository.findById(rateBookReview.getReviewid()).get();
        if (bookstoreUser!=null){
            service.rateBookReview(rateBookReview.getValue(),review);
        }
        return ("redirect:/books/"+review.getBook().getSlug());
    }
}
