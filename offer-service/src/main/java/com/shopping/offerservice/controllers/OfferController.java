package com.shopping.offerservice.controllers;



import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @GetMapping("/test")
    public String test(Authentication auth){
        return "hello world!" + auth.getPrincipal();
    }
}
