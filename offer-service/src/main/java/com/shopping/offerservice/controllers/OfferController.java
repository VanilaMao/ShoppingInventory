package com.shopping.offerservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @GetMapping("/test")
    public String test(){
        return "hello world!";
    }
}
