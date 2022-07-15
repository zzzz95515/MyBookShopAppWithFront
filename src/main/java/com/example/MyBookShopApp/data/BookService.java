package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<Book> getBooksData() {
        List<Book> books = jdbcTemplate.query("Select * from books", (ResultSet rs, int rowNum) -> {
            Book book =new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(getAuthorByAuthorId(rs.getInt("author_id")));
//            book.setAuthor(rs.getInt("author_id"));
            book.setTitle(rs.getString("title"));
            book.setPrice_old(rs.getString("price_old"));
            book.setPrice(rs.getString("price"));
            return book;
        });
//        for (Book book: books){
//            List<Author> authors =  jdbcTemplate.query("Select * from authors where authors.id = " + String.valueOf(book.getAuthor_id()),(ResultSet rs, int rowNum) -> {
//                Author author = new Author();
//                author.setLastName(rs.getString("last_name"));
//                author.setFirstName(rs.getString("first_name"));
//                return author;
//            });
////            book.setAuthorName(authors.get(0).getLastName()+' '+authors.get(0).getFirstName());
//        }
        return new ArrayList<>(books);
    }

    private String getAuthorByAuthorId(int author_id) {
        List<Author> authors = jdbcTemplate.query("SELECT * from authors where authors.id="+author_id,(ResultSet rs, int rowNum) ->{
            Author author= new Author();
            author.setId(rs.getInt("id"));
            author.setLast_name(rs.getString("last_name"));
            author.setFirst_name(rs.getString("first_name"));
            return author;
        });
        return authors.get(0).toString();
    }

    public List<Book> getPopularBookData(){
        return getBooksData();
    }

    public List<Book> getNewBookData(){
        return getBooksData();
    }


}
