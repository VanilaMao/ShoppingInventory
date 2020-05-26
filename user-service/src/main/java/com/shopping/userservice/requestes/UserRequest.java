package com.shopping.userservice.requestes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  //ObjectMapper mapper = new ObjectMapper(); mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
public class UserRequest {
    private String username;
    private String password;
    private String zipcode;
}
