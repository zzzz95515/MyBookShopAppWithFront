package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class BooksRateController {

    private final BookRepository repository;
    private final BookRateRepository rateRepository;

    private final BookReviewsRepository reviewsRepository;


    public BooksRateController(BookRepository repository, BookRateRepository rateRepository, BookReviewsRepository reviewsRepository) {
        this.repository = repository;
        this.rateRepository = rateRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @PostMapping("/rateBook")
    public String rateBook(@RequestParam("bookId") String bookSlug, @RequestParam("value")Integer value){
        Book book = repository.findBookBySlug(bookSlug);
        rateRepository.save(new BookRateEntity(value,book));
        return ("redirect:/books/"+bookSlug);
    }

    @PostMapping("/bookReview")
    public String reviewBook(@RequestParam("bookId") String slug, @RequestParam("text") String reviewText,
                             @CookieValue(name = "guestName", required = false) String guestName,
                             HttpServletResponse response){
        String guestsName=guestName;
        if (guestName==null || guestName.equals("")){
            String newGuestName = "newGuest"+response.hashCode();
            guestsName=newGuestName;
            Cookie cookie = new Cookie("guestName",newGuestName);
            response.addCookie(cookie);
        }
        Book book = repository.findBookBySlug(slug);
        Date date = new Date();
        BookReviewsEnt review = new BookReviewsEnt(0,0,reviewText,guestsName,book,date);
        reviewsRepository.save(review);
        return ("redirect:/books/"+slug);
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
