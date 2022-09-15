package com.example.MyBookShopApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationPageTests {

    private static WebDriver driver;

    @BeforeAll
    static void setup(){
        System.setProperty("webdriver.chrome.driver","C:/Users/Stariy_ork/downloads/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    public void testRegistrationPage() throws InterruptedException {
        RegistrationPage page = new RegistrationPage(driver);

        page
                .callPage()
                .pause();
        assertTrue(driver.getPageSource().contains("Регистрация"));
    }


    @Test
    public void testRegistrationNewUser() throws InterruptedException {
        RegistrationPage page = new RegistrationPage(driver);
        page
                .callPage()
                .pause()
                .setUpRegistrarionToken("88005553535","phone")
                .pause()
                .click("name")
                .pause()
                .click("submitPhone")
                .pause()
                .setUpRegistrarionToken("123456","phoneCode")
                .pause()
                .setUpRegistrarionToken("test@mail.ru","mail")
                .pause()
                .click("name")
                .pause()
                .click("submitMail")
                .pause()
                .setUpRegistrarionToken("111111","mailCode")
                .pause()
                .setUpRegistrarionToken("Test User","name")
                .pause()
                .setUpRegistrarionToken("123456","pass")
                .pause()
                .submit("registration")
                .pause();
        assertTrue(driver.getPageSource().contains("Регистрация прошла успешно"));
    }

}