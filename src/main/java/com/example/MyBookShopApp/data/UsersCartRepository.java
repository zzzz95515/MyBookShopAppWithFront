package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersCartRepository extends JpaRepository<UsersCartAndPostponed,Integer> {
    UsersCartAndPostponed findByUserEmail(String email);
}
