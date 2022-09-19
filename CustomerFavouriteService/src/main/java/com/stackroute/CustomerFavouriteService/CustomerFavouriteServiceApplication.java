package com.stackroute.CustomerFavouriteService;

import com.stackroute.CustomerFavouriteService.jwtfilter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class CustomerFavouriteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerFavouriteServiceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> filterBean = new FilterRegistrationBean<>();
		filterBean.setFilter(new JwtFilter());

		filterBean.addUrlPatterns("/movieapp/v1/favourite/*");
		return filterBean;
	}
}
