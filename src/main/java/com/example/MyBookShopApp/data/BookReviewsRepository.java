package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewsRepository extends JpaRepository<BookReviewsEnt, Integer> {

    List<BookReviewsEnt> findAllByBook_Slug(String slug);
}
