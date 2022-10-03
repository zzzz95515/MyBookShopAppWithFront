package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BooksController {



    private final BookstoreUserRegister userRegister;



    private final BookRepository bookRepository;
    private final ResourceStorage storage;

    private final BookRateRepository bookRateRepository;

    private final BookReviewsRepository bookReviewsRepository;

    private final ViewedBookRepo viewedBookRepo;
    private final ViewedBooksRepo viewedBooksRepo;

    public BooksController(BookstoreUserRegister userRegister, BookRepository bookRepository, ResourceStorage storage, BookRateRepository bookRateRepository, BookReviewsRepository bookReviewsRepository, ViewedBookRepo viewedBookRepo, ViewedBooksRepo viewedBooksRepo) {
        this.userRegister = userRegister;
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.bookRateRepository = bookRateRepository;
        this.bookReviewsRepository = bookReviewsRepository;
        this.viewedBookRepo = viewedBookRepo;
        this.viewedBooksRepo = viewedBooksRepo;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model){
        Book bookBySlug= bookRepository.findBookBySlug(slug);
        bookBySlug.setIsBestseller(bookBySlug.getIsBestseller()+1);
        BookstoreUser user= (BookstoreUser) userRegister.getCurrentUser();
        bookRepository.save(bookBySlug);
        Date date = new Date();
        if (user!=null){
            LastViewedBooks lastViewedBooks =viewedBooksRepo.findByBookstoreUser(user);
            boolean flag=true;
            List<ViewedBook> list;
            if (lastViewedBooks!=null){
                list = lastViewedBooks.getViewedBooks();
                for (ViewedBook viewedBook: list){
                    if (viewedBook.getBook().getSlug().equals(slug)){
                        viewedBook.setDate(new Date(System.currentTimeMillis()+1000*60*60));
                        flag=false;
                    }
                    if (viewedBook.getDate().before(date)){
                        list.remove(viewedBook);
                    }
                }
                if (flag){
                    ViewedBook vb=new ViewedBook();
                    vb.setBook(bookBySlug);
                    vb.setDate(new Date(System.currentTimeMillis()+1000*60*60));
                    viewedBookRepo.save(vb);
                    list.add(vb);
                }
            }
            else {
                lastViewedBooks=new LastViewedBooks();
                lastViewedBooks.setBookstoreUser(user);
                list=new ArrayList<>();
                ViewedBook vb=new ViewedBook();
                vb.setBook(bookBySlug);
                vb.setDate(new Date(System.currentTimeMillis()+1000*60*60));
                viewedBookRepo.save(vb);
                list.add(vb);
            }

            lastViewedBooks.setViewedBooks(list);
            viewedBooksRepo.save(lastViewedBooks);
        }
        model.addAttribute("bookBySlug",bookBySlug);
        List<BookRateEntity> ratesList=bookRateRepository.findAllByBook_Slug(slug);
        model.addAttribute("reviews",bookReviewsRepository.findAllByBook_Slug(slug));
        Integer totalNum=ratesList.size();
        Integer rate1=0;
        Integer rate2=0;
        Integer rate3=0;
        Integer rate4=0;
        Integer rate5=0;
        Double totalRate=0.0;
        if (totalNum!=0){
            for (BookRateEntity rates: ratesList){
                switch (rates.getRateValue()){
                    case (1):
                        rate1++;
                        break;
                    case (2):
                        rate2++;
                        break;
                    case (3):
                        rate3++;
                        break;
                    case (4):
                        rate4++;
                        break;
                    case (5):
                        rate5++;
                        break;
                    default:
                        rate5++;
                        break;
                }
            }
            totalRate= Double.valueOf((rate1+2*rate2+rate3*3+rate4*4+rate5*5)/totalNum);
        }
        model.addAttribute("bookRate1",rate1);
        model.addAttribute("bookRate2",rate2);
        model.addAttribute("bookRate3",rate3);
        model.addAttribute("bookRate4",rate4);
        model.addAttribute("bookRate5",rate5);
        model.addAttribute("totalRate",totalRate);
        model.addAttribute("totalNum",totalNum);
        return "/books/slug";
    }




    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImg(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {

        String savePath = storage.saveNewBookImage(file,slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate);

        return ("redirect:/books/"+slug);
    }




    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book path is: "+ path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book mime is: "+ mediaType);

        byte[] data = storage.getBookFileArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data length is: "+ data.length);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + path
                .getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @ModelAttribute("authFaildForRate")
    public Boolean isNotAuthentificated(){
        if (userRegister.getCurrentUser()!=null){
            return false;
        }
        else {
            return true;
        }

    }

    @PostMapping("/rateBook")
    public String rateBook(@RequestBody RateBookResponce bookResponce, Model model){
        if (userRegister.getCurrentUser()!=null){
            Book book = bookRepository.findBookBySlug(bookResponce.getBookId());
            bookRateRepository.save(new BookRateEntity(bookResponce.getValue(),book));
        }
        else {
            throw new UsernameNotFoundException("зарегистрируйтесь, чтобы оставить оценку");
        }
        return ("redirect:/books/"+bookResponce.getBookId());
    }

    @PostMapping("/bookReview")
    public String reviewBook(@RequestParam("bookId") String slug, @RequestParam("text") String reviewText,
                             @CookieValue(name = "guestName", required = false) String guestName,
                             HttpServletResponse response){
        String guestsName=guestName;
        if (guestName==null || guestName.equals("")){
            String newGuestName = "newGuest"+response.hashCode();
            guestsName=newGuestName;
            Cookie cookie = new Cookie("guestName",newGuestName);
            response.addCookie(cookie);
        }
        Book book = bookRepository.findBookBySlug(slug);
        Date date = new Date();
        BookReviewsEnt review = new BookReviewsEnt(0,0,reviewText,guestsName,book,date);
        bookReviewsRepository.save(review);
        return ("redirect:/books/"+slug);
    }

    @PostMapping("/rateBookReview")
    public String rateReview(@RequestParam("reviewid") Integer reviewId, @RequestParam("value") Integer value){
        BookReviewsEnt review = bookReviewsRepository.getById(reviewId);
        switch (value){
            case (1):
                review.setPosRate(review.getPosRate()+1);
                break;
            case (-1):
                review.setNegRate(review.getNegRate()+1);
                break;
        }
        bookReviewsRepository.save(review);
        return ("redirect:/books/"+review.getBook().getSlug());
    }
}
