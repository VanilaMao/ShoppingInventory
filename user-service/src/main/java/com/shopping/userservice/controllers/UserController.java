package com.shopping.userservice.controllers;
import com.shopping.userservice.domains.AuthenticationUser;
import com.shopping.userservice.entities.User;
import com.shopping.userservice.requestes.UserRequest;
import com.shopping.userservice.respositories.UserRepository;
import com.shopping.userservice.services.ProducerService;
import com.shopping.userservice.utilities.JwtHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController()
@RequestMapping("${common.basePath}")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private ProducerService service;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/create")
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

    @GetMapping("/user-info")
    public  User userInfo(Authentication auth){
        UUID userId = JwtHelper.getUserId(auth);
        logger.info("user info - userid:"+userId);
        return userRepository.findById(userId).get();
    }

    @PostMapping("/user/delete")
    public ResponseEntity<String> deleteUser(Authentication auth){
        UUID userId = JwtHelper.getUserId(auth);
        logger.info("delete user - userid:"+userId);
        userRepository.deleteById(userId);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(Authentication auth,@RequestBody UserRequest userRequest){
        UUID userId = JwtHelper.getUserId(auth);
        logger.info("update user - userid:"+userId);
        User user = userRepository.findById(userId).get();


        if(user == null){
           return new ResponseEntity<>("bad user format", HttpStatus.BAD_REQUEST);
        }


        // use map struct to map value
        user.setZipcode(userRequest.getZipcode());

        userRepository.save(user);
        return ResponseEntity.ok("success");
    }
}
