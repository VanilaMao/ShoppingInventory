package com.shopping.userservice.requestes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String zipcode;
}
