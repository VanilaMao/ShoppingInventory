package com.shopping.authservice.Configs;

import com.shopping.authservice.entities.User;
import com.shopping.authservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

// https://medium.com/takeaway-tech/wiring-your-own-authentication-with-springbootoauth2-a636b7420714 legacyTokenService
// https://stackoverflow.com/questions/44909073/authenticationprovider-and-userdetailsservice-used-by-default-by-spring-security
// https://stackoverflow.com/questions/33509978/spring-security-authenticationprovider-and-userdetailsservice-not-working-as-ex
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByName(name);
        if(user!=null){
            if(passwordEncoder.matches(password,user.getPassword())){
                return new UsernamePasswordAuthenticationToken(
                        user.getId(), password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
