package com.stackroute.AuthenticationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.stackroute.AuthenticationService")).build().apiInfo(getInfo());
	}

	public ApiInfo getInfo() {
		ApiInfoBuilder apiBuild = new ApiInfoBuilder();
		apiBuild.title("Authentication Service for MovieStore app").version("1.0").license("vlad@qualitest");
		return apiBuild.build();

	}

}
