package com.stackroute.feedbackservice.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class LoggerAspect {
	Logger log = LoggerFactory.getLogger(AspectConfig.class);


	@Before("execution (* com.stackroute.feedbackservice.controller.FeedbackController.createFeedback(..))")
	public void doAccessCheck(JoinPoint jp) {
		log.info("Going to register a user " + jp.toString());
	}

	@After("execution (* com.stackroute.feedbackservice.controller.FeedbackController.createFeedback(..))")
	public void afterDoAccessCheck(JoinPoint jp) {
		log.info("One Feedback was added " + jp.toString());
		System.out.println("Agruments Passed=" + Arrays.toString(jp.getArgs()));
	}


	@AfterThrowing(value = "execution (* com.stackroute.feedbackservice.controller.FeedbackController.createFeedback(..))", throwing="excepobj")
	public void handleExceptionWhenCreateUser(Exception excepobj)
	{
		log.warn("Some Exceptions raised " + excepobj.getMessage());
	}


	@Before("execution (* com.stackroute.feedbackservice.controller.FeedbackController.deleteFeedback(..))")
	public void doLoginCheck(JoinPoint jp) {
		log.info("Going to delete a feedback " + jp.toString());
	}

	@After("execution (* com.stackroute.feedbackservice.controller.FeedbackController.deleteFeedback(..))")
	public void afterLoginCheck(JoinPoint jp) {
		log.info("One feedback was deleted with success " + jp.toString());
	}


	@AfterThrowing(value = "execution (* com.stackroute.feedbackservice.controller.FeedbackController.deleteFeedback(..))", throwing="excepobj")
	public void handleExceptionWhenLogIn(Exception excepobj)
	{
		log.warn("Some Exceptions raised " + excepobj.getMessage());
	}
}
