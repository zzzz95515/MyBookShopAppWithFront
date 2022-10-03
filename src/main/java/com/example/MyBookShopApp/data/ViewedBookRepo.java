package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewedBookRepo extends JpaRepository<ViewedBook,Integer> {
}
