package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    public List<Author>findAuthorById(Integer id);

}
