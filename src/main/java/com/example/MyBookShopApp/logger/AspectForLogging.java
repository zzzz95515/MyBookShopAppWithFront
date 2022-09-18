package com.example.MyBookShopApp.logger;

import com.example.MyBookShopApp.security.BookstoreUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AspectForLogging {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.logger.LogForSavingMethods)")
    public void saveObjectLogger(){
    }

    @AfterReturning(pointcut = "saveObjectLogger()",returning = "response")
    public void logSave(JoinPoint joinPoint, Object response){
        logger.info("function " + joinPoint.toShortString() + " finish work");
        logger.info("saved object is " + response.toString());
    }

    @Pointcut(value = "@annotation(com.example.MyBookShopApp.logger.CurrentUser)")
    public void currentUser(){
    }

    @AfterReturning(pointcut = "currentUser()",returning = "response")
    public void logCurUser(JoinPoint joinPoint, BookstoreUser response){
        logger.info("current user is: " + response.toString());
    }

    @Pointcut(value = "within(com.example.MyBookShopApp.controllers.*)")
    public void allControllers(){

    }

    @Before(value = "allControllers()")
    public void controllerSratr(JoinPoint joinPoint){
        logger.info(joinPoint.getTarget().toString() + " start  work with method " + joinPoint.toShortString());
    }
}
