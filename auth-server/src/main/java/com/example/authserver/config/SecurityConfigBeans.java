package com.example.authserver.config;

import com.example.authserver.api.domain.ClientDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;

import javax.servlet.Filter;

@Configuration
@Order(100)
public class SecurityConfigBeans {

    @Bean
    @ConfigurationProperties("google")
    public ClientDetails google() {
        return new ClientDetails();
    }

    @Bean
    public OAuth2RestTemplate restTemplate(ClientDetails details, @Qualifier("oauth2ClientContext") OAuth2ClientContext context){
        return new OAuth2RestTemplate(details.getClient(), context);
    }

    @Bean
    public AuthoritiesExtractor googleAuthExtractor(OAuth2RestTemplate template){
        return map -> {
            //TODO check email using userDetailsService etc.
            String mail = (String) map.get("email");
            System.out.println("AUTHORITIES EXTRACTOR  - Processing user with mail: " + mail);
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            //throw new BadCredentialsException("Not in Spring Team");
        };
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
