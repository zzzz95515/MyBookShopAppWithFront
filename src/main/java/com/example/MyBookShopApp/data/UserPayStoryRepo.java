package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPayStoryRepo extends JpaRepository<UserPayStory,Integer> {
    UserPayStory findUserPayStoryByStoryUser(BookstoreUser bookstoreUser);
}
