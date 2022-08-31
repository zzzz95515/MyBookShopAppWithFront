package com.example.MyBookShopApp.security;

import liquibase.pro.packaged.B;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserRegister {

    private final BookstoreUserRepository bookstoreUserRepository;

    private final PasswordEncoder passwordEncoder;

    public BookstoreUserRegister(BookstoreUserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
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
}
