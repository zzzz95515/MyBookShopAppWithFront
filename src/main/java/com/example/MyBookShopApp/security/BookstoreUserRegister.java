package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserRegister {

    private final AuthenticationManager authenticationManager;
    private final BookstoreUserRepository bookstoreUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final BookstoreUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    public BookstoreUserRegister(AuthenticationManager authenticationManager, BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder, BookstoreUserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public void registerNewUser(RegistrationForm registrationForm){
        if (bookstoreUserRepository.findBookstoreUserByEmail(registrationForm.getEmail())==null){
            BookstoreUser user = new BookstoreUser();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));

            bookstoreUserRepository.save(user);
        }
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
