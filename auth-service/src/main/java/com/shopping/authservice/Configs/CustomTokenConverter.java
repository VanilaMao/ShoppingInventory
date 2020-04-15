package com.shopping.authservice.Configs;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        //if(authentication.getOAuth2Request().getGrantType().equalsIgnoreCase("password")) {
        //User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put("test", "test-field");

        ((DefaultOAuth2AccessToken) accessToken)
                .setAdditionalInformation(additionalInfo);
       // }
        accessToken = super.enhance(accessToken,authentication);
        ((DefaultOAuth2AccessToken) accessToken)
                .setAdditionalInformation(new HashMap<>());
        return accessToken;
    }
}
