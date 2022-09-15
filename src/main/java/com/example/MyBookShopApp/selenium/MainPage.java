package com.example.MyBookShopApp.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {

    private String url="http://localhost:8085/";
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver=driver;
    }

    public MainPage callPage() {
        driver.get(url);
        return this;
    }

    public MainPage getPage(){
        driver.get(url);
        return this;
    }

    public MainPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public MainPage setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));
        element.sendKeys(token);
        return this;
    }

    public MainPage submit() {
        WebElement element = driver.findElement(By.id("search"));
        element.submit();
        return this;
    }

    public MainPage clickTo(String linkText){
        WebElement element = driver.findElement(By.linkText(linkText));
        element.click();
        return this;
    }
}
