package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class LastViewedBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private BookstoreUser bookstoreUser;
    @OneToMany
    private List<ViewedBook> viewedBooks;

    public BookstoreUser getBookstoreUser() {
        return bookstoreUser;
    }

    public void setBookstoreUser(BookstoreUser bookstoreUser) {
        this.bookstoreUser = bookstoreUser;
    }

    public List<ViewedBook> getViewedBooks() {
        return viewedBooks;
    }

    public void setViewedBooks(List<ViewedBook> viewedBooks) {
        this.viewedBooks = viewedBooks;
    }
}
