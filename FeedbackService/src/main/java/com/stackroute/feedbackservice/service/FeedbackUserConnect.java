package com.stackroute.feedbackservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("authenticationService")
public interface FeedbackUserConnect {
	@RequestMapping(method = RequestMethod.GET, value = "movieapp/v1/auth/getUserEmail/{email}")
	String getUserById(@PathVariable String email);
}
