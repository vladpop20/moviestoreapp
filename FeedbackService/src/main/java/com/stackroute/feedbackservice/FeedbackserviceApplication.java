package com.stackroute.feedbackservice;

import com.stackroute.feedbackservice.jwtfilter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeedbackserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackserviceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> filterBean = new FilterRegistrationBean<>();
		filterBean.setFilter(new JwtFilter());

		filterBean.addUrlPatterns("/movieapp/v1/feedback/*");
		return filterBean;
	}

}
