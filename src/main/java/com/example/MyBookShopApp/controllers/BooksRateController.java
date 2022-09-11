package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.Date;

@Controller
public class BooksRateController {

    private final BookRepository repository;
    private final BookRateRepository rateRepository;

    private final BookReviewsRepository reviewsRepository;

    private final BookstoreUserRegister userRegister;


    public BooksRateController(BookRepository repository, BookRateRepository rateRepository, BookReviewsRepository reviewsRepository, BookstoreUserRegister userRegister) {
        this.repository = repository;
        this.rateRepository = rateRepository;
        this.reviewsRepository = reviewsRepository;
        this.userRegister = userRegister;
    }

    @PostMapping("/rateBook")
    public String rateBook(@RequestBody RateBookResponce bookResponce, Model model){
        if (userRegister.getCurrentUser()!=null){
            Book book = repository.findBookBySlug(bookResponce.getBookId());
            rateRepository.save(new BookRateEntity(bookResponce.getValue(),book));
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
            Book book = repository.findBookBySlug(bookResponce.getBookId());
            Date date = new Date();
            BookReviewsEnt review = new BookReviewsEnt(0,0,bookResponce.getText(), bookstoreUser.getName(),book,date);
            reviewsRepository.save(review);
        }

        return ("redirect:/books/"+bookResponce.getBookId());
    }

    @PostMapping("/rateBookReview")
    public String rateReview(@RequestParam("reviewid") Integer reviewId, @RequestParam("value") Integer value){
        BookReviewsEnt review = reviewsRepository.getById(reviewId);
        switch (value){
            case (1):
                review.setPosRate(review.getPosRate()+1);
                break;
            case (-1):
                review.setNegRate(review.getNegRate()+1);
                break;
        }
        reviewsRepository.save(review);
        return ("redirect:/books/"+review.getBook().getSlug());
    }
}
