package com.example.MyBookShopApp.security;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.awt.print.Book;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookstoreUserRegisterTests {

    private final BookstoreUserRegister userRegister;
    private final PasswordEncoder passwordEncoder;
    private RegistrationForm registrationForm;

    private ContactConfirmationPayLoad payload = new ContactConfirmationPayLoad();

    @MockBean
    private BookstoreUserRepository bookstoreUserRepositoryMock;

    @Autowired
    public BookstoreUserRegisterTests(BookstoreUserRegister userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm=new RegistrationForm();
        registrationForm.setEmail("sv@yandex.ru");
        registrationForm.setName("test use1r");
        registrationForm.setPhone("79059999999");
        registrationForm.setPass("999999");
        payload.setCode("123456");
        payload.setContact("zzzz95515@gmail.com");

    }

    @AfterEach
    void tearDown() {
        payload=null;
        registrationForm=null;
    }

    @Test
    void registerNewUser() {
        BookstoreUser user = userRegister.registerNewUser(registrationForm);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(registrationForm.getPass(),user.getPassword()));
        assertTrue( user.getPhone().matches(registrationForm.getPhone()));
        assertTrue(user.getName().matches(registrationForm.getName()));
        assertTrue(user.getEmail().matches(registrationForm.getEmail()));
        Mockito.verify(bookstoreUserRepositoryMock,Mockito.times(1))
                .save(Mockito.any(BookstoreUser.class));
    }

    @Test
    void registerNewUserFail(){
        Mockito.doReturn(new BookstoreUser())
                .when(bookstoreUserRepositoryMock)
                .findBookstoreUserByEmail(registrationForm.getEmail());
        BookstoreUser user = userRegister.registerNewUser(registrationForm);
        assertNull(user);
    }


}