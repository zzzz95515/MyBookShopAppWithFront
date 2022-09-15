package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BookRateReviewService {

    private final BookRepository bookRepository;
    private final BookReviewsRepository reviewsRepository;
    private final BookRateRepository rateRepository;

    public BookRateReviewService(BookRepository bookRepository, BookReviewsRepository reviewsRepository, BookRateRepository rateRepository) {
        this.bookRepository = bookRepository;
        this.reviewsRepository = reviewsRepository;
        this.rateRepository = rateRepository;
    }

    public String saveBookRate(RateBookResponce bookResponce){
        Book book = bookRepository.findBookBySlug(bookResponce.getBookId());
        rateRepository.save(new BookRateEntity(bookResponce.getValue(),book));
        return bookResponce.getBookId();
    }

    public String saveBookReview(ReviewBookResponce bookResponce, BookstoreUser bookstoreUser){
        Book book = bookRepository.findBookBySlug(bookResponce.getBookId());
        Date date = new Date();
        BookReviewsEnt review = new BookReviewsEnt(0,0,bookResponce.getText(), bookstoreUser.getName(),book,date);
        reviewsRepository.save(review);
        return bookResponce.getBookId();
    }

    public String rateBookReview(Integer value, BookReviewsEnt review){
        String resp="";
        Integer posRate=review.getPosRate();
        Integer negRate=review.getNegRate();
        switch (value){
            case (1):
                review.setPosRate(posRate+1);
                resp="+1";
                break;
            case (-1):
                review.setNegRate(negRate+1);
                resp="-1";
                break;
            default:resp="Incorrect";
        }
        reviewsRepository.save(review);
        return resp;
    }


}
