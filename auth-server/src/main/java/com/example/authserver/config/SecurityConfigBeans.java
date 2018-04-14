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

    /**
     * This bean can be used to check the user info just before considering him authenticated, for example to
     * block users whose mail does not appear in your database.
     * @param template  rest template bean injected by Spring.
     * @return  AuthoritiesExtractor instance, able to generate a list of authorities, or refuse the user.
     */
    @Bean
    public AuthoritiesExtractor googleAuthExtractor(OAuth2RestTemplate template){
        return map -> {
            //Get data and check it, e.g. by autowiring a UserService
            String mail = (String) map.get("email");
            System.out.println("AUTHORITIES EXTRACTOR  - Processing user with mail: " + mail);

            //Generate a list of authorities from roles or refuse the user by
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            //throw new BadCredentialsException("I don't like him");
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
