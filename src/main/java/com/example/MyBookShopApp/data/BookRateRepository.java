package com.example.MyBookShopApp.data;

import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookRateRepository extends JpaRepository<BookRateEntity,Integer> {

    List<BookRateEntity> findAllByBook_Slug(String slug);
}
