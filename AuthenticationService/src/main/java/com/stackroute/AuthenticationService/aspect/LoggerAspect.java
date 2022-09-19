package com.stackroute.AuthenticationService.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
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


	@Before("execution (* com.stackroute.AuthenticationService.controller.UserAuthController.createUser(..))")
	public void doAccessCheck(JoinPoint jp) {
		log.info("Going to register a user " + jp.toString());
	}

	@After("execution (* com.stackroute.AuthenticationService.controller.UserAuthController.createUser(..))")
	public void afterDoAccessCheck(JoinPoint jp) {
		log.info("One user was added " + jp.toString());
		System.out.println("Agruments Passed=" + Arrays.toString(jp.getArgs()));
	}


	@AfterReturning(value = "execution (* com.stackroute.AuthenticationService.controller.UserAuthController.createUser(..))", returning = "retVal")
	public Object afterUserCreationReturn(Object retVal) {

		ResponseEntity<?> responseEntity=(ResponseEntity<?>) retVal;

		String user=(String) responseEntity.getBody();

		log.info("New user is about to be added: " + user);

		return retVal;
	}

	@AfterThrowing(value = "execution (* com.stackroute.AuthenticationService.controller.UserAuthController.createUser(..))", throwing="excepobj")
	public void handleExceptionWhenCreateUser(Exception excepobj)
	{
		log.warn("Some Exceptions raised " + excepobj.getMessage());
	}


	@Before("execution (* com.stackroute.AuthenticationService.controller.UserAuthController.loginUser(..))")
	public void doLoginCheck(JoinPoint jp) {
		log.info("Going to login as a user " + jp.toString());
	}

	@After("execution (* com.stackroute.AuthenticationService.controller.UserAuthController.loginUser(..))")
	public void afterLoginCheck(JoinPoint jp) {
		log.info("One user was logged with token= " + jp.toString());
	}


	@AfterReturning(value = "execution (* com.stackroute.AuthenticationService.controller.UserAuthController.loginUser(..))", returning = "retVal")
	public Object afterLoginHappenedReturn(Object retVal) {

		ResponseEntity<?> responseEntity=(ResponseEntity<?>) retVal;

		Map<String, String> user = null;
		ObjectMapper mapper = new ObjectMapper();

		user = mapper.convertValue(responseEntity.getBody(), HashMap.class);

		log.info("@@@@ User is about to be logged with token: " + user);

		return retVal;
	}

	@AfterThrowing(value = "execution (* com.stackroute.AuthenticationService.controller.UserAuthController.loginUser(..))", throwing="excepobj")
	public void handleExceptionWhenLogIn(Exception excepobj)
	{
		log.warn("Some Exceptions raised " + excepobj.getMessage());
	}

}
