package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.security.BookstoreUser;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import liquibase.pro.packaged.D;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
//@RequestMapping("/books")
public class BookshopCartController {


    @ModelAttribute(name = "bookCart")
    public List<Book>BookCart(){
        return new ArrayList<>();
    }

    private final BookRepository bookRepository;

    private final BookstoreUserRegister userRegister;

    private final UsersCartRepository cartRepository;

    private final PaymentService paymentService;

    private final UserPayStoryRepo payStoryRepo;

    private final TransactionRepo transactionRepo;
    public BookshopCartController(BookRepository bookRepository, BookstoreUserRegister userRegister, UsersCartRepository cartRepository, PaymentService paymentService, UserPayStoryRepo payStoryRepo, TransactionRepo transactionRepo) {
        this.bookRepository = bookRepository;
        this.userRegister = userRegister;
        this.cartRepository = cartRepository;
        this.paymentService = paymentService;
        this.payStoryRepo = payStoryRepo;
        this.transactionRepo = transactionRepo;
    }


    @PostMapping("/books/changeBookStatuscart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable(name = "slug") String slug, @CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model){
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        if (user!=null){
            UsersCartAndPostponed cart = cartRepository.findByUserEmail(user.getEmail());
            if (cart==null){
                cart=new UsersCartAndPostponed();
                cart.setUserEmail(user.getEmail());
            }
            Book book =bookRepository.findBookBySlug(slug);
            cart.getCartBookList().remove(book);
            cartRepository.save(cart);
            if (cart.getCartBookList().isEmpty()){
                model.addAttribute("isCartEmpty",true);
            }else {
                model.addAttribute("isCartEmpty",false);
            }
        }
        else {
            removeBookFromCartCookie(cartContents,slug,response,model);
        }

        return "redirect:/books/cart";
    }

    private void removeBookFromCartCookie(String cartContents, String slug, HttpServletResponse response, Model model){
        if (cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents",String.join("/",cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            if (cookieBooks.size()>0){
                model.addAttribute("isCartEmpty",false);
            }
            else {
                model.addAttribute("isCartEmpty",true);
            }

        }
        else {
            model.addAttribute("isCartEmpty",true);
        }
    }


    @PostMapping("/books/changeBookStatus/cart/{slug}")
    public String handleChangeBookStatus(@PathVariable(name = "slug") String slug, @CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model){
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        if (user!=null){
            UsersCartAndPostponed cart = cartRepository.findByUserEmail(user.getEmail());
            if (cart==null){
                cart=new UsersCartAndPostponed();
                cart.setUserEmail(user.getEmail());
            }
            Book book =bookRepository.findBookBySlug(slug);
            cart.getCartBookList().add(book);
            cartRepository.save(cart);
            if (cart.getCartBookList().isEmpty()){
                model.addAttribute("isCartEmpty",true);
            }else {
                model.addAttribute("isCartEmpty",false);
            }
        }else {
            addToCartCookies(slug,cartContents,response,model);
        }

        return "redirect:/books/" + slug;
    }

    public void addToCartCookies(String slug, String cartContents, HttpServletResponse response, Model model){
        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
    }

    @GetMapping("/books/cart")
    public String handleCartRequest(@CookieValue(name = "cartContents", required = false) String cartContents, Model model){
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        if (user!=null){
            UsersCartAndPostponed cart = cartRepository.findByUserEmail(user.getEmail());
            if (cart==null){
                cart=new UsersCartAndPostponed();
                cart.setUserEmail(user.getEmail());
            }
            model.addAttribute("bookCart", cart.getCartBookList());
        }
        else {
            if (cartContents==null || cartContents.equals("")){
                model.addAttribute("isCartEmpty",true);
            } else {
                model.addAttribute("isCartEmpty",false);
                cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
                cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length()-1) : cartContents;
                String[] cookiesSlugs = cartContents.split("/");
                List<Book> booksFromCookiesSlugs = bookRepository.findBooksBySlugIn(cookiesSlugs);
                model.addAttribute("bookCart", booksFromCookiesSlugs);
            }
        }


        return "cart";
    }



    @GetMapping("/books/pay")
    public RedirectView handlePay(@CookieValue(name = "cartContents", required = false) String cartContents) throws NoSuchAlgorithmException {
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        List<Book> books;
        if (user!=null){
            UsersCartAndPostponed cart = cartRepository.findByUserEmail(user.getEmail());
            if (cart==null){
                cart=new UsersCartAndPostponed();
                cart.setUserEmail(user.getEmail());
            }
            books = cart.getCartBookList();
            Double price = books.stream().mapToDouble(Book::discountPrice).sum();
            UserPayStory story = payStoryRepo.findUserPayStoryByStoryUser(user);
            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setOperationType("покупка книг");
            transaction.setTotalSum(price);
            List<Transaction> transactionList=story.getUsersTransactions();
            transactionList.add(transaction);
            story.setUsersTransactions(transactionList);
            transactionRepo.save(transaction);
            payStoryRepo.save(story);



        }
        else {
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length()-1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");
            books = bookRepository.findBooksBySlugIn(cookiesSlugs);


        }
        String paymentUrl = paymentService.getPaymentUrl(books);
        return new RedirectView(paymentUrl);
    }










    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }
}
