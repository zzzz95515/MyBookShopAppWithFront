package com.example.MyBookShopApp.security;


import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.security.jwt.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AuthUserController {

    private final BlackListRepository blackListRepository;
    private final BookstoreUserRegister userRegister;

    private final SmsService smsService;

    private final JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;

    private final UserPayStoryRepo storyRepo;

    private final TransactionRepo transactionRepo;

    private final PaymentService paymentService;
    @Autowired
    public AuthUserController(BlackListRepository blackListRepository, BookstoreUserRegister userRegister, SmsService smsService, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, UserPayStoryRepo storyRepo, TransactionRepo transactionRepo, PaymentService paymentService) {
        this.blackListRepository = blackListRepository;
        this.userRegister = userRegister;
        this.smsService = smsService;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.storyRepo = storyRepo;
        this.transactionRepo = transactionRepo;
        this.paymentService = paymentService;
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

    @PostMapping("/requestEmailConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestEmailConfirmation(@RequestBody ContactConfirmationPayLoad payLoad){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zzzz95515@mail.ru");
        message.setTo(payLoad.getContact());
        SmsCode smsCode = new SmsCode(smsService.generateCode(), 300);
        smsService.saveNewCode(smsCode);
        message.setSubject("BookStore mail verification!");
        message.setText("verification code is: "+smsCode.getCode());
        javaMailSender.send(message);
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
        if (payLoad.getContact().contains("@")){
            if (smsService.verifyCode(payLoad.getCode())){
                response.setResult("true");
                return response;
            }
            else {
                return new ContactConfirmationResponse();
            }
        }
        else {
            response.setResult("true");
            return response;
        }

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

    @PostMapping("/profile")
    public RedirectView rebuildProfile(Model model, @RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "password", required = false) String password,
                                 @RequestParam(value = "password", required = false) String passwordReply,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "sum", required = false) Double sum) throws NoSuchAlgorithmException {
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();

        if (sum!=null && sum>0){
            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setTotalSum(sum);
            String paymentUrl = paymentService.getPaymentUrl(sum);
            transaction.setOperationType("Пополнение на сумму");
            UserPayStory story = storyRepo.findUserPayStoryByStoryUser(user);
            List<Transaction> transactionList = story.getUsersTransactions();
            transactionList.add(transaction);
            transactionRepo.save(transaction);
            story.setUsersTransactions(transactionList);
            storyRepo.save(story);
//            return new RedirectView(paymentUrl);
            return new RedirectView("/profile");
        }
        else {
            if (name!=null && !name.equals("")){
                user.setName(name);
            }
            if (email!=null && !email.equals("")){
                user.setEmail(email);
            }
            if (phone!=null && !phone.equals("")){
                user.setPhone(phone);
            }
            if (password!=null && !password.equals("") && password.length()>5 && password.equals(passwordReply)){
                user.setPassword(passwordEncoder.encode(password));
            }
            return new RedirectView("/profile");
        }


    }



    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @ModelAttribute("story")
    public List<Transaction> transactions(){
        BookstoreUser user = (BookstoreUser) userRegister.getCurrentUser();
        UserPayStory story = storyRepo.findUserPayStoryByStoryUser(user);
        return story.getUsersTransactions();
    }
}
