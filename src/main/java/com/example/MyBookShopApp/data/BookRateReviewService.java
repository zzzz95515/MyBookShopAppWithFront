package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.logger.LogForSavingMethods;
import com.example.MyBookShopApp.security.BookstoreUser;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @LogForSavingMethods
    public BookRateEntity saveBookRate(RateBookResponce bookResponce){
        Book book = bookRepository.findBookBySlug(bookResponce.getBookId());
        BookRateEntity review = new BookRateEntity(bookResponce.getValue(),book);
        rateRepository.save(review);
        return review;
    }

    @LogForSavingMethods
    public BookReviewsEnt saveBookReview(ReviewBookResponce bookResponce, BookstoreUser bookstoreUser){
        Book book = bookRepository.findBookBySlug(bookResponce.getBookId());
        Date date = new Date();
        BookReviewsEnt review = new BookReviewsEnt(0,0,bookResponce.getText(), bookstoreUser.getName(),book,date);
        reviewsRepository.save(review);
        return review;
    }


    @LogForSavingMethods
    public BookReviewsEnt rateBookReview(Integer value, BookReviewsEnt review){
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
        return review;
    }


}
