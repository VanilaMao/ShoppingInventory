package com.shopping.userservice.controllers;

import com.shopping.userservice.entities.Group;
import com.shopping.userservice.entities.User;
import com.shopping.userservice.entities.UserGroup;
import com.shopping.userservice.entities.compositeKeys.UserGroupId;
import com.shopping.userservice.enums.ActiveStatus;
import com.shopping.userservice.enums.GroupUserStatus;
import com.shopping.userservice.responses.GroupResponse;
import com.shopping.userservice.respositories.GroupRepository;
import com.shopping.userservice.respositories.UserRepository;
import com.shopping.userservice.utilities.JwtHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("${common.basePath}")
public class GroupController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/group/join")
    public ResponseEntity<?> joinGroup(Authentication auth, @RequestBody String name){
        UUID userId = JwtHelper.getUserId(auth);
        Optional<Group> groupOptional = groupRepository.findByName(name);
        if(!groupOptional.isPresent()){
            return new ResponseEntity<>("Group does not exist",HttpStatus.BAD_REQUEST);
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Group group = groupOptional.get();
        User user = userOptional.get();
        if(group.getUserGroups().stream().anyMatch(x->x.getUser().getId() ==userId)){
            return new ResponseEntity<>("Already Joined in the Group, waiting for approval",HttpStatus.BAD_REQUEST);
        }
        group.getUserGroups().add(UserGroup.builder()
                .id(UserGroupId.builder().userId(user.getId()).groupId(group.getGroupId()).build())
                .group(group)
                .status(GroupUserStatus.Requested)
                .user(user)
                .build());
        groupRepository.save(group);
        return new ResponseEntity<>(GroupResponse.builder()
                        .status(GroupUserStatus.Requested.toString())
                        .description(group.getDescription())
                        .name(group.getName()).build(),HttpStatus.OK);
    }

    @PostMapping("/group/create")
    public ResponseEntity<?> createGroup(Authentication auth, @RequestBody String name){

        UUID userId = JwtHelper.getUserId(auth);
        Optional<Group> group = groupRepository.findByName(name);
        if(group.isPresent()){
            return new ResponseEntity<>("Group Name is already Taken",HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Group newGroup = Group.builder()
                .groupId(UUID.randomUUID())
                .description("a new group")
                .name(name)
                .status(ActiveStatus.Active)
                .userGroups(new ArrayList<>())
                .owner(user.get()).build();
        Group savedGroup = groupRepository.save(newGroup);
        return new ResponseEntity<>(GroupResponse.builder()
                .name(savedGroup.getName())
                .description(savedGroup.getDescription())
                .status(savedGroup.getStatus().toString()).build(),HttpStatus.OK);
    }
}
