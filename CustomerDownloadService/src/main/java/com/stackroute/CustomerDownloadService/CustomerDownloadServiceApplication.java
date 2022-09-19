package com.stackroute.CustomerDownloadService;

import com.stackroute.CustomerDownloadService.jwfilter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CustomerDownloadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerDownloadServiceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		FilterRegistrationBean<JwtFilter> filterBean = new FilterRegistrationBean<>();
		filterBean.setFilter(new JwtFilter());

		filterBean.addUrlPatterns("/movieapp/v1/download/*");
		return filterBean;
	}

}
