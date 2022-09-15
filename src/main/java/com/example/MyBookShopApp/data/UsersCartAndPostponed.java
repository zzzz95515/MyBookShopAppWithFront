package com.example.MyBookShopApp.data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UsersCartAndPostponed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String userEmail;

    @OneToMany
    private List<Book> cartBookList= new ArrayList<>();

    @OneToMany
    private List<Book> postPonedBookList= new ArrayList<>();

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<Book> getCartBookList() {
        return cartBookList;
    }

    public void setCartBookList(List<Book> cartBookList) {
        this.cartBookList = cartBookList;
    }

    public List<Book> getPostPonedBookList() {
        return postPonedBookList;
    }

    public void setPostPonedBookList(List<Book> postPonedBookList) {
        this.postPonedBookList = postPonedBookList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
