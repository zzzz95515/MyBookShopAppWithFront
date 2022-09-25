package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.TransactionRepo;
import com.example.MyBookShopApp.data.UserPayStory;
import com.example.MyBookShopApp.data.UserPayStoryRepo;
import com.example.MyBookShopApp.logger.CurrentUser;
import com.example.MyBookShopApp.logger.LogForSavingMethods;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookstoreUserRegister {

    private final AuthenticationManager authenticationManager;
    private final BookstoreUserRepository bookstoreUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final BookstoreUserDetailsService userDetailsService;

    private final UserPayStoryRepo storyRepo;

    private final TransactionRepo transactionRepo;
    private final JWTUtil jwtUtil;

    public BookstoreUserRegister(AuthenticationManager authenticationManager, BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder, BookstoreUserDetailsService userDetailsService, UserPayStoryRepo storyRepo, TransactionRepo transactionRepo, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.storyRepo = storyRepo;
        this.transactionRepo = transactionRepo;
        this.jwtUtil = jwtUtil;
    }

    @LogForSavingMethods
    public BookstoreUser registerNewUser(RegistrationForm registrationForm){
        if (bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail())==null){
            BookstoreUser user = new BookstoreUser();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            user.setTotalCash(0.0);
            UserPayStory story = new UserPayStory();
            story.setStoryUser(user);
            story.setUsersTransactions(new ArrayList<>());
            bookstoreUserRepository.save(user);
            storyRepo.save(story);
            return user;
        }
        return null;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayLoad payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) userDetailsService.loadUserByEmail(payload.getContact());

        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }




    public ContactConfirmationResponse login(ContactConfirmationPayLoad payload) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @CurrentUser
    public Object getCurrentUser() {
        BookstoreUserDetails userDetails;
        try {
            userDetails = (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e){
            try {
                DefaultOAuth2User defaultOAuth2User =
                        (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                BookstoreUser bookstoreUser = new BookstoreUser();
                bookstoreUser.setName(defaultOAuth2User.getAttribute("name"));
                bookstoreUser.setEmail(defaultOAuth2User.getAttribute("email"));
                userDetails = new BookstoreUserDetails(bookstoreUser);
            }
            catch (Exception ex){
                userDetails=new BookstoreUserDetails(null);
            }

        }

        return userDetails.getBookstoreUser();
    }
}
