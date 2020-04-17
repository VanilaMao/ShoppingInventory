package com.shopping.userservice.controllers;
import com.shopping.userservice.domains.AuthenticationUser;
import com.shopping.userservice.entities.User;
import com.shopping.userservice.requestes.UserRequest;
import com.shopping.userservice.respositories.UserRepository;
import com.shopping.userservice.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController()
@RequestMapping("${common.basePath}")
public class UserController {

    @Autowired
    private ProducerService service;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String test(Authentication auth, @RequestBody UserRequest userRequest){
        UUID userId = UUID.randomUUID();
        service.sendMessage(AuthenticationUser.builder()
                .name(userRequest.getUsername())
                .password(userRequest.getPassword())
                .id(userId).build()
        );
        userRepository.save(User.builder().id(userId).zipcode("48197").build());
        return "success";
    }
}
