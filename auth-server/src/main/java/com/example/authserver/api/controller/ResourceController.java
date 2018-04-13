package com.example.authserver.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @GetMapping("user")
    public Principal user(Principal user){
        return user;
    }

    @GetMapping("resource")
    public List<String> things(){
        return Arrays.asList("Potatoes","Tomatoes", "Carrots", "Spaghett");
    }
}
