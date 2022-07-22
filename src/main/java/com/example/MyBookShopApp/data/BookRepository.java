package com.example.MyBookShopApp.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findBooksByAuthor_FirstName(String name);
    List<Book> findBooksByAuthor_FirstNameContaining(String authorFirstName);
    List<Book> findBooksByTitleContaining(String title);

    List<Book> findBooksByPriceOldBetween(Double min, Double max);

    List<Book> findBooksByPriceOldIs(Double price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)",nativeQuery = true)
    List<Book> getBookWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String searchWord, Pageable nextPage);
}
