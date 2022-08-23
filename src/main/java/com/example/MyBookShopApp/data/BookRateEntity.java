package com.example.MyBookShopApp.data;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "bookRates")
public class BookRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rate")
    @NotNull
    private Integer rateValue;

    public BookRateEntity(Integer rateValue, Book book) {
        this.rateValue = rateValue;
        this.book = book;
    }

    @ManyToOne
    @JoinColumn(name = "book_slug",referencedColumnName = "slug")
    @NotNull
    private Book book;

    public BookRateEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public void setRateValue(Integer rateValue) {
        this.rateValue = rateValue;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
