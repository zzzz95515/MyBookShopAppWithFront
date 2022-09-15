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
class MainPageSeleniumTests {

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
    public void testMainPageAccess() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("BOOKSHOP"));
    }

    @Test
    public void testMainPageSearchByQuery() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause()
                .setUpSearchToken("King")
                .pause()
                .submit()
                .pause();
        assertTrue(driver.getPageSource().contains("King of Kings"));
    }

    @Test
    public void testNavigation() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause()
                .clickTo("Genres")
                .pause()
                .clickTo("News")
                .pause()
                .clickTo("Popular")
                .pause()
                .clickTo("Authors")
                .pause()
                .clickTo("Main")
                .pause()
                .clickTo("King of Kings");
    }

}