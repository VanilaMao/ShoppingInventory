package com.shopping.userservice.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;

import java.util.Map;
import java.util.UUID;

public class JwtHelper {
    // eventually have to get it from token additional field , get from auth.getPrincipal() is not guaranteed
    public static UUID getUserId(Authentication auth){

//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> map =
//                objectMapper.convertValue(auth.getDetails(), Map.class);
//        Jwt jwtToken = org.springframework.security.jwt.JwtHelper.decode((String) map.get("tokenValue"));
        return UUID.fromString(auth.getPrincipal().toString());
    }
}
