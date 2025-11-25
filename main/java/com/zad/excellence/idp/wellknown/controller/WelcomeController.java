package com.zad.excellence.idp.wellknown.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wellknown") 
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the Wellknown Service of Identity Provider!";
    }
}
