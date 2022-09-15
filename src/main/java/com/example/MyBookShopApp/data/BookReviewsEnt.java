package com.example.MyBookShopApp.data;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookReviews")
public class BookReviewsEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rewiewPosRate")
    private Integer posRate;

    @Column(name = "rewiewNegRate")
    private Integer negRate;

    public Integer getPosRate() {
        return posRate;
    }

    public void setPosRate(Integer posRate) {
        this.posRate = posRate;
    }

    @Column(name = "review")
    @NotNull
    private String review;

    @Column(name = "usersName")
    @NotNull
    private String usersName;

    @Override
    public String toString() {
        return "BookReviewsEnt{" +
                "id=" + id +
                ", posRate=" + posRate +
                ", negRate=" + negRate +
                ", review='" + review + '\'' +
                ", usersName='" + usersName + '\'' +
                ", book=" + book +
                ", date=" + date +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "book_slug",referencedColumnName = "slug")
    @NotNull
    private Book book;

    @Column(name = "date")
    private Date date;

    public BookReviewsEnt() {

    }

    public Integer getNegRate() {
        return negRate;
    }

    public void setNegRate(Integer negRate) {
        this.negRate = negRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String user) {
        this.usersName = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BookReviewsEnt(Integer posRate, Integer negRate, String review, String usersName, Book book, Date date) {
        this.posRate = posRate;
        this.negRate = negRate;
        this.review = review;
        this.usersName = usersName;
        this.book = book;
        this.date = date;
    }
}
