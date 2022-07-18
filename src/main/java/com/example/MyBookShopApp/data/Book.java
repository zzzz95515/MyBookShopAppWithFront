package com.example.MyBookShopApp.data;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;



@NotNull
    private String title;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Transient
    private String price_old;

//    @NotNull
    private String price;

//    @NotNull
    private Date pub_date;

//    @NotNull
    private Integer is_bestseller;

//    @NotNull
    private String slug;

    private String image;

    private String discription;

//    @NotNull
    private Integer discount=0;





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice_old() {
        return price_old;
    }

    public void setPrice_old(String priceOld) {
        this.price_old = priceOld;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    public Integer getIs_bestseller() {
        return is_bestseller;
    }

    public void setIs_bestseller(Integer is_bestseller) {
        this.is_bestseller = is_bestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String text) {
        this.discription = text;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", price_old='" + price_old + '\'' +
                ", price='" + price + '\'' +
                ", pub_date=" + pub_date +
                ", is_bestseller=" + is_bestseller +
                ", slug='" + slug + '\'' +
                ", image='" + image + '\'' +
                ", text='" + discription + '\'' +
                ", discount=" + discount +
                '}';
    }
}
