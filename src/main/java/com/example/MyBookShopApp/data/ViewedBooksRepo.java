package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedBooksRepo extends JpaRepository<LastViewedBooks,Integer> {
    LastViewedBooks findByBookstoreUser(BookstoreUser user);


}
