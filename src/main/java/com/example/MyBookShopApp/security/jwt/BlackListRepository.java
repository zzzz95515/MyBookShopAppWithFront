package com.example.MyBookShopApp.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository  extends JpaRepository<JWTBlackList,Integer> {
    JWTBlackList findByToken(String token);
}
