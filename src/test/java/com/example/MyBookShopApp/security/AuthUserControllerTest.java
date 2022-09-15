package com.example.MyBookShopApp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class AuthUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Model model;
    private RegistrationForm registrationForm;
    private final BookstoreUserRegister userRegister;
    private final PasswordEncoder passwordEncoder;
    private ContactConfirmationPayLoad payload = new ContactConfirmationPayLoad();

    @Autowired
    AuthUserControllerTest(BookstoreUserRegister userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setEmail("test@mail.org");
        registrationForm.setName("Tester");
        registrationForm.setPass("iddqd");
        registrationForm.setPhone("9031232323");

        model = new ExtendedModelMap();

        payload.setCode("iddqd");
        payload.setContact("test@mail.org");
    }

    @Test
    void handleUserRegistration() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        mockMvc.perform(post("/reg").headers(headers).flashAttr("registrationForm",
                        registrationForm).flashAttr("model", model))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isOk());
    }

    @Test
    void handleLogin() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("Content-Type", "application/json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(payload);

        mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON_UTF8)
                        .headers(httpHeaders)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isOk());
    }

    @Test
    void login() {
        ContactConfirmationResponse response = userRegister.login(payload);
        assertEquals(response.getResult(),"true");
    }

    @Test
    void jwtLogin() {
        ContactConfirmationResponse response = userRegister.jwtLogin(payload);
        assertEquals(response.getResult().split("\\.").length,3);
    }




}