package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getBooksData(){
        return repository.findAll();
    }

    public List<Book> getPopularBookData(){

        return repository.getBestsellers();
    }

    public List<Book> getNewBookData(){
        return getBooksData();
    }

    public List<Book> getBooksByAuthor(String authorName){
        return repository.findBooksByAuthor_FirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title){
        return repository.findBooksByTitleContaining(title);
    }

    public List<Book> getBooksWithPriceBetween(Double min, Double max){
        return repository.findBooksByPriceOldBetween(min,max);
    }

    public List<Book> getBooksWithPrice(Double price){
        return repository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxDiscount(){
        return repository.getBookWithMaxDiscount();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset,Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return repository.findAll(nextPage);
    }
}
