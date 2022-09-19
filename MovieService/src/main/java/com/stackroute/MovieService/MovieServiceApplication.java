package com.stackroute.MovieService;

import com.stackroute.MovieService.jwtfilter.JwtFilterAdmin;
import com.stackroute.MovieService.jwtfilter.JwtFilterCustomer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class MovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}


//	@Bean
//	public FilterRegistrationBean<JwtFilterCustomer> jwtFilterCustomer() {
//		FilterRegistrationBean<JwtFilterCustomer> filterBean = new FilterRegistrationBean<>();
//		filterBean.setFilter(new JwtFilterCustomer());
//
//		filterBean.addUrlPatterns("/movieapp/v1/movie/customer/*");
//		return filterBean;
//	}

	@Bean
	public FilterRegistrationBean<JwtFilterAdmin> jwtFilterAdmin() {
		FilterRegistrationBean<JwtFilterAdmin> filterBean = new FilterRegistrationBean<>();
		filterBean.setFilter(new JwtFilterAdmin());

		filterBean.addUrlPatterns("/movieapp/v1/movie/admin/*");
		return filterBean;
	}

}
