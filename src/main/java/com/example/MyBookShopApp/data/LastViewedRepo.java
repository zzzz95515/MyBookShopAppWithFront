package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LastViewedRepo extends JpaRepository<LastViewedBooks,Integer> {

    Page<LastViewedBooks> findByBookstoreUser(BookstoreUser user, Pageable nextPage);
}
