package com.stackroute.MovieService.aspect;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    @Bean
    public LoggerAspect getAspect()
    {
        return new LoggerAspect();
    }
}
