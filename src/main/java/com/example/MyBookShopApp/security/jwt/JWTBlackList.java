package com.example.MyBookShopApp.security.jwt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "blacklist")
public class JWTBlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    public JWTBlackList(String token) {
        this.token = token;
    }

    String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
