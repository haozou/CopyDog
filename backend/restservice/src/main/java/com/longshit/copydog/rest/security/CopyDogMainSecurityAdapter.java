package com.longshit.copydog.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

/**
 * CopyDog security adapter
 */
@Order(2)
@Configuration
@EnableWebSecurity
class CopyDogMainSecurityAdapter extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CopyDogMainSecurityAdapter.class);

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**",
            "/login",
            "/login/**",
    };

    @Autowired
    OauthUtils oauthUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(a -> a
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login().successHandler((httpServletRequest, httpServletResponse, authentication) -> {

                    oauthUtils.registerOauthUser((OAuth2User) authentication.getPrincipal());
            DefaultSavedRequest savedRequest = (DefaultSavedRequest) httpServletRequest.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            httpServletResponse.sendRedirect(savedRequest.getRequestURI());

        });

    }
}
