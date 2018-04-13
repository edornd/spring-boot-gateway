package com.example.authserver.config;

import com.example.authserver.api.domain.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2ClientContext clientContext;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    private ClientDetails googleDetails;
    private OAuth2RestTemplate restTemplate;
    private AuthoritiesExtractor authoritiesExtractor;

    @Autowired
    public SecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService,
                          @Qualifier("oauth2ClientContext") OAuth2ClientContext clientContext,
                          PasswordEncoder passwordEncoder, ClientDetails googleDetails,
                          OAuth2RestTemplate restTemplate,
                          AuthoritiesExtractor authoritiesExtractor) {
        this.clientContext = clientContext;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.googleDetails = googleDetails;
        this.restTemplate = restTemplate;
        this.authoritiesExtractor = authoritiesExtractor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()
                .logout()
                    .logoutSuccessUrl("/").permitAll().and()
                .formLogin()
                    .loginPage("/").and()
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());//.and()
                //.addFilterBefore(ssoFilter("/login/google", googleDetails), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(basicAuthProvider());
    }

    @Bean
    public DaoAuthenticationProvider basicAuthProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    private Filter ssoFilter(String path, ClientDetails details){
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(details.getResource().getUserInfoUri(), details.getClient().getClientId());
        tokenServices.setAuthoritiesExtractor(authoritiesExtractor);
        tokenServices.setRestTemplate(restTemplate);
        filter.setRestTemplate(restTemplate);
        filter.setTokenServices(tokenServices);
        return filter;
    }
}
