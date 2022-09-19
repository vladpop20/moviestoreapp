package com.stackroute.MovieService.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {


    Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.stackroute.MovieService.controller.MovieController.addMovie())")
    public void addMovie() {
    }

    @Before("addMovie()")
    public void beforeAddNews(JoinPoint jp) {
        log.info("Going to add a movie " + jp.toString());
    }

    @After("addMovie()")
    public void afterAddNews(JoinPoint jp) {
        log.info("After added movies" + jp.toString());
    }

    @AfterReturning("addMovie()")
    public void afterReturningAddNews(JoinPoint jp) {
        log.info("After returning added movies" + jp.toString());
    }

    @AfterThrowing(pointcut = "addMovie()", throwing = "excepobj")
    public void handleExceptionInAddNews(Exception excepobj) {
        log.warn("News controller exception in add movie raised " + excepobj.getMessage());
    }

}
