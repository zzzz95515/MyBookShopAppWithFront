package com.example.MyBookShopApp.security;


import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.Book;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;

    public AuthUserController(BookstoreUserRegister userRegister) {
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
        response.setResult(true);
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
        response.setResult(true);
        return response;
    }


    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayLoad payload){
        return userRegister.login(payload);
    }

    @GetMapping("/my")
    public String handleMy(){
        return "/my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("curUser",userRegister.getCurrentUser());
        return "/profile";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextHolder.clearContext();
        if (session!=null){
            session.invalidate();
        }

        for (Cookie cookie : request.getCookies()){
            cookie.setMaxAge(0);
        }
        return "redirect:/";
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
