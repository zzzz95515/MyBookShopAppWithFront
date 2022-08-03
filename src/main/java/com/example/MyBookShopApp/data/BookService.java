package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getBooksData(){
        return repository.findAll();
    }

    public List<Book> getPopularBookData(){

        return repository.getBestsellers();
    }

    public List<Book> getNewBookData(){
        return getBooksData();
    }

    public List<Book> getBooksByAuthor(String authorName){
        return repository.findBooksByAuthor_FirstNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.equals("")||title.length()<=1){
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        }else {
            List<Book> result= repository.findBooksByTitleContaining(title);
            if (result.size()>0){
                return result;
            }else {
                throw new BookstoreApiWrongParameterException("No data found with this parameters");
            }

        }
    }

    public List<Book> getBooksWithPriceBetween(Double min, Double max){
        return repository.findBooksByPriceOldBetween(min,max);
    }

    public List<Book> getBooksWithPrice(Double price){
        return repository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxDiscount(){
        return repository.getBookWithMaxDiscount();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset,Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return repository.findAll(nextPage);
    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return repository.findBookByTitleContaining(searchWord,nextPage);
    }


    public List<Book> getPageOfPopularBooks(Integer offset,Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return repository.findBookByIsBestsellerEquals(1,nextPage).getContent();
    }

    public List<Book> getPageOfNewBooks(Integer offset,Integer limit){
        Date date= new Date("2019/09/29");
        Pageable nextPage = PageRequest.of(offset,limit);
        List<Book> bookList= repository.findBookByPubDateAfter(date,nextPage).getContent();
        return bookList;
    }

    public List<Book> getPageOfRecentBooksByDates(String date1, String date2, Integer offset, Integer limit){
        date1 = date1.replace('.','/');
        date2 = date2.replace('.','/');
        Date firstDate=new Date(date1);
        Date secondDate=new Date(date2);
        Pageable nextPage = PageRequest.of(offset,limit);
        List<Book> resultlist=repository.findBookByPubDateBetweenOrderByPubDate(firstDate,secondDate,nextPage).getContent();
        return  resultlist;
    }

    public List<Book> getPageOfPopularBooksOrderBy(Integer offset, Integer limit){
        Pageable nextPage=PageRequest.of(offset,limit);
        return repository.findBookByIsBestsellerGreaterThanOrderByIsBestseller(0,nextPage).getContent();
    }

    public List<Book> getPageOfBooksByTag(String tag, Integer offset, Integer limit){
        Pageable nextPage=PageRequest.of(offset,limit);
        return repository.findBookByTag_Tag(tag,nextPage).getContent();
    }

    public List<Book> getPageOfBooksByGenre(String genre, Integer offset, Integer limit){
        Pageable nextPage=PageRequest.of(offset,limit);
        return repository.findBookByGenre_Genre(genre,nextPage).getContent();
    }

    public List<Book>getPageOfAuthorsBooks(Integer author_id, Integer offset, Integer limit){
        Pageable nextPage=PageRequest.of(offset,limit);
        return repository.findBookByAuthor_Id(author_id,nextPage).getContent();
    }

}
