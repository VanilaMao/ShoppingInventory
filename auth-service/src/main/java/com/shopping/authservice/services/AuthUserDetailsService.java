package com.shopping.authservice.services;

import com.shopping.authservice.entities.User;
import com.shopping.authservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username is the principle of CustomAuthenticationProvider, user_id
        if(username==null || username.isEmpty()){
            throw new UsernameNotFoundException("User not found.");
        }
        User user = userRepository.findById(UUID.fromString(username)).get();
        if(user == null){
            throw new UsernameNotFoundException("User not found.");
        }
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(username);
        return builder.password(user.getPassword()).roles("ADMIN").build();
    }
}
