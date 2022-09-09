package com.example.MyBookShopApp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final BookstoreUserRepository bookstoreUserRepository;

    public BookstoreUserDetailsService(BookstoreUserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        BookstoreUser bookstoreUser = bookstoreUserRepository.findBookstoreUserByName(s);
        if (bookstoreUser!=null){
            return new BookstoreUserDetails(bookstoreUser);
        }else {
            BookstoreUser bookstoreUserbyEmail = bookstoreUserRepository.findBookstoreUserByEmail(s);
            if (bookstoreUserbyEmail!=null){
                return new BookstoreUserDetails(bookstoreUserbyEmail);
            }else {
                throw new UsernameNotFoundException("user not found");
            }
        }
    }


    public UserDetails loadUserByEmail(String s) throws UsernameNotFoundException {
        BookstoreUser bookstoreUser = bookstoreUserRepository.findBookstoreUserByEmail(s);
        if (bookstoreUser!=null){
            return new BookstoreUserDetails(bookstoreUser);
        }else {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
