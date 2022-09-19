package com.stackroute.feedbackservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("movieservice")
public interface FeedbackMovieConnect {
	@RequestMapping(method = RequestMethod.GET, value = "movieapp/v1/movie/customer/getMovieById/{movieId}")
	Integer getMovieById(@PathVariable int movieId);
}
