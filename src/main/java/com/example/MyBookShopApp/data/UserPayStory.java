package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.security.BookstoreUser;


import javax.persistence.*;
import java.util.List;

@Entity
public class UserPayStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private BookstoreUser storyUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Transaction> getUsersTransactions() {
        return usersTransactions;
    }

    public void setUsersTransactions(List<Transaction> usersTransactions) {
        this.usersTransactions = usersTransactions;
    }

    @OneToMany
    private List< Transaction> usersTransactions;

    public BookstoreUser getStoryUser() {
        return storyUser;
    }

    public void setStoryUser(BookstoreUser uuser) {
        this.storyUser = uuser;
    }



    public UserPayStory() {
    }
}
