package com.stackroute.CustomerDownloadService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/*As in this class we are implementing Swagger So annotate the class with @Configuration and
 * @EnableSwagger2
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*
     * Annotate this method with @Bean . This method will return an Object of Docket.
     * This method will implement logic for swagger
     */

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket getdocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getdetails())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stackroute.CustomerDownloadService"))
                .paths(PathSelectors.any())
                .build();
    }
    public ApiInfo getdetails() {
        ApiInfoBuilder apibuild = new ApiInfoBuilder();
        apibuild.title("Feedback Service for MovieStore app").version("1");
        return apibuild.build();
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}