package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@EnableOAuth2Sso
@EnableZuulProxy
@SpringBootApplication
@RestController
public class ClientApplication  {

    @GetMapping("/me")
    public Principal user(Principal user){
        return user;
    }

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}
