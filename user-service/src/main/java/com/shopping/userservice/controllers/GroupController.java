package com.shopping.userservice.controllers;

import com.shopping.userservice.respositories.GroupRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("${common.basePath}")
public class GroupController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private GroupRepository groupRepository;

//
//    @PostMapping("/group/create")
//    public ResponseEntity<String> createGroup(GroupRequest){
//
//    }
}
