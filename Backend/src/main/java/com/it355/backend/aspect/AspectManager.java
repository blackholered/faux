package com.it355.backend.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectManager {

    //@AfterThrowing(pointcut = "execution(* com.it355.backend.service.*(..))", throwing = "ex")
    @AfterThrowing(value = " execution(* com.it355.backend.service.*.*(..))", throwing = "ex")
    public void handleException(Exception ex) {
        // Handle the exception here
        System.out.println("An exception occurred: " + ex.getMessage());
    }
}