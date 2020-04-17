package com.shopping.offerservice.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// https://stackoverflow.com/questions/45231203/spring-framework-where-to-parse-jwt-for-custom-claim

@RestController
public class OfferController {

    @GetMapping("/test")
    public String test(Authentication auth){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map =
                objectMapper.convertValue(auth.getDetails(), Map.class);
        Jwt jwtToken = JwtHelper.decode((String) map.get("tokenValue"));
        return "hello world!" + auth.getPrincipal();
    }
}
