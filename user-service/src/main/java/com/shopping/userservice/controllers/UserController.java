package com.shopping.userservice.controllers;
import com.shopping.userservice.domains.AuthenticationUser;
import com.shopping.userservice.entities.Group;
import com.shopping.userservice.entities.User;
import com.shopping.userservice.entities.UserGroup;
import com.shopping.userservice.enums.ActiveStatus;
import com.shopping.userservice.enums.Role;
import com.shopping.userservice.requestes.UserRequest;
import com.shopping.userservice.responses.GroupResponse;
import com.shopping.userservice.responses.UserResponse;
import com.shopping.userservice.respositories.GroupRepository;
import com.shopping.userservice.respositories.UserGroupRepository;
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

import java.util.*;


@RestController()
@RequestMapping("${common.basePath}")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private ProducerService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @PostMapping("/user/create")
    public ResponseEntity<String> test(Authentication auth, @RequestBody UserRequest userRequest){

        User existingUser = userRepository.findByName(userRequest.getUsername());
        if(existingUser!=null){
            return new ResponseEntity("User already exist",HttpStatus.BAD_REQUEST);
        }

        UUID userId = UUID.randomUUID();
        service.sendMessage(AuthenticationUser.builder()
                .name(userRequest.getUsername())
                .password(userRequest.getPassword())
                .id(userId).build()
        );

        User user = User.builder()
                .id(userId)
                .name(userRequest.getUsername())
                .status(ActiveStatus.Pending)
                .roles(new HashSet<>(Arrays.asList(Role.Guest)))
                .zipcode("48197").build();
        userRepository.save(user);
        return ResponseEntity.ok("success");
    }

    // for editing
    @GetMapping("/user")
    public  User user(Authentication auth){
        UUID userId = JwtHelper.getUserId(auth);
        logger.info("user info - userid:"+userId);
        return userRepository.findById(userId).get();
    }

    // for dashboard
    @GetMapping("/user-info")
    public  ResponseEntity<?> userInfo(Authentication auth){
        UUID userId = JwtHelper.getUserId(auth);
        logger.info("user info - userid:"+userId);
        Optional<User> userOptional =  userRepository.findById(userId);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>("User does not exist",HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();

        List<GroupResponse> doctorGroupResponses = new ArrayList<>();

        //groups those doctor created
        if(user.getRoles().contains(Role.Doctor)){
            List<Group> doctorGroups = groupRepository.findByOwner(user);
            doctorGroups.stream().forEach(x->doctorGroupResponses.add(
                    GroupResponse.builder()
                            .name(x.getName())
                            .description(x.getDescription())
                            .status(x.getStatus().toString()).build()
            ));
        }

        //doctor's groups those  user joined in
        List<GroupResponse> userGroupResponses = new ArrayList<>();
        List<UserGroup> userGroups = userGroupRepository.findByUser(user);
        userGroups.stream().forEach(x->userGroupResponses.add(
                GroupResponse.builder()
                .name(x.getGroup().getName())
                .description(x.getGroup().getDescription())
                .status(x.getStatus().toString()).build()));


        return new ResponseEntity<>(UserResponse.builder()
                .name(user.getName())
                .status(user.getStatus())
                .roles(new ArrayList(user.getRoles()))
                .doctorGroups(doctorGroupResponses)
                .userGroups(userGroupResponses)
                .build(),HttpStatus.OK);

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

    @PostMapping("/user/apply-doctor")
    public ResponseEntity<String> applyDoctor(Authentication auth){
        UUID userId = JwtHelper.getUserId(auth);
        logger.info("update user - userid:"+userId);
        User user = userRepository.findById(userId).get();

        if(user == null){
            return new ResponseEntity<>("bad user format", HttpStatus.BAD_REQUEST);
        }

        if(user.getRoles().contains(Role.Doctor)){
            return new ResponseEntity<>("already a doctor", HttpStatus.BAD_REQUEST);
        }

        // todo: determine if an user is active first
        // use map struct to map value
        user.getRoles().add(Role.Doctor);
        userRepository.save(user);
        return ResponseEntity.ok("success");
    }

}
