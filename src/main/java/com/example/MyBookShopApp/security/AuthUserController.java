package com.example.MyBookShopApp.security;


import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.security.jwt.BlackListRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthUserController {

    private final BlackListRepository blackListRepository;
    private final BookstoreUserRegister userRegister;

    public AuthUserController(BlackListRepository blackListRepository, BookstoreUserRegister userRegister) {
        this.blackListRepository = blackListRepository;
        this.userRegister = userRegister;
    }

    @GetMapping("/signin")
    public String handleSignIn(){
        return "/signin";
    }

    @GetMapping("/signup")
    public String handleSignup(Model model){
        model.addAttribute("regForm", new RegistrationForm());
        return "/signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayLoad payLoad){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }



    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model){
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk",true);
        return "/signin";
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayLoad payLoad){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }


    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayLoad payload, HttpServletResponse response){
        ContactConfirmationResponse loginResponce = userRegister.jwtLogin(payload);
        Cookie cookie= new Cookie("token", loginResponce.getResult());
        cookie.setMaxAge(1000*60*60*24*365);
        response.addCookie(cookie);
        return loginResponce;
    }

    @GetMapping("/my")
    public String handleMy(){
        return "/my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("curUser",userRegister.getCurrentUser());
        return "profile";
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
