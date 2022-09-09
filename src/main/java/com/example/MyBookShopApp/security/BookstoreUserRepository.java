package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreUserRepository extends JpaRepository<BookstoreUser,Integer> {
    BookstoreUser findBookstoreUserByEmail(String email);

    BookstoreUser findBookstoreUserByName(String name);
}
