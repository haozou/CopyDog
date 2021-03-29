package com.longshit.copydog.rest.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.awt.print.Pageable;
import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * RestAPI documentation UI
 */
@Configuration
public class CopyDogAPIDocConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/v1.0/.*";
    private final Logger logger = LoggerFactory.getLogger(CopyDogAPIDocConfiguration.class);

    @Bean
    public Docket copyDogApi() {
        Contact contact = new Contact(
                "Long Shit",
                "https://www.longshit.com",
                "longshit@gmail.com");

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfo(
                "Copy Dog Backend API",
                "This is the Rest API of Copy Dog Service",
                "0.1.0",
                "https://www.longshit.com",
                contact,
                "Apache 2.0",
                "https://https://www.longshit.com",
                vext);


        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(createSecuritySchemes())
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
        return docket;
    }

    private List<SecurityScheme> createSecuritySchemes() {
        List<SecurityScheme> schemes = new ArrayList<>();
        schemes.add(apiKeyScheme());
        schemes.add(oauthScheme());
        return schemes;
    }

    private ApiKey apiKeyScheme() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private OAuth oauthScheme() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("global", "accessEverything"));


        List<GrantType> grantTypes = new ArrayList();
        GrantType creGrant = new AuthorizationCodeGrant(
                new TokenRequestEndpoint("http://localhost:8080/oauth2/authorization/github", "clientId", "clientSecret"),
                new TokenEndpoint("http://localhost:8080/oauth2/access_token", "access_token"));

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("JWT", authorizationScopes));
        securityReferences.add(new SecurityReference("oauth2schema", authorizationScopes));
        return securityReferences;
    }


    @Bean
    public SecurityConfiguration securityInfo() {
        return SecurityConfigurationBuilder
                .builder()
                .clientId("")
                .clientSecret("")
                .build();
    }


}