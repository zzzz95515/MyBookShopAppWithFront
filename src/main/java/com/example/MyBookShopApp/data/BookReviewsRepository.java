package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookReviewsRepository extends JpaRepository<BookReviewsEnt, Integer> {

    List<BookReviewsEnt> findAllByBook_Slug(String slug);


}
