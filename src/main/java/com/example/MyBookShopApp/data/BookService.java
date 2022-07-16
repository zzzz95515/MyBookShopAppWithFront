package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
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
        return getBooksData();
    }

    public List<Book> getNewBookData(){
        return getBooksData();
    }


}
