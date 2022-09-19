package com.stackroute.AuthenticationService.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

	@Bean
	public LoggerAspect getLogger() {
		return new LoggerAspect();
	}
}
