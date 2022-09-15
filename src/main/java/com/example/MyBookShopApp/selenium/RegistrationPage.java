package com.example.MyBookShopApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPage {
    private String url="http://localhost:8085/signup";
    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public RegistrationPage callPage() {
        driver.get(url);
        return this;
    }

    public RegistrationPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public RegistrationPage setUpRegistrarionToken(String token, String id) {
        WebElement nameElement = driver.findElement(By.id(id));
        nameElement.sendKeys(token);
        return this;
    }

    public RegistrationPage submit(String id) {
        WebElement element = driver.findElement(By.id(id));
        element.submit();
        return this;
    }

    public RegistrationPage click(String id){
        WebElement element = driver.findElement(By.id(id));
        element.click();
        return this;
    }

}
