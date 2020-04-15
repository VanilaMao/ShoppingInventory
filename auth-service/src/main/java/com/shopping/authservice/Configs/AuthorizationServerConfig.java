package com.shopping.authservice.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.inMemory()
                .withClient("clientapp")
                .authorizedGrantTypes("password","authorization_code","refresh_token")
                .scopes("read","write")
                .autoApprove(true)
                .secret(passwordEncoder.encode("zmhshopping"));
    }

    // need enhance token and Jwttoken
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
        System.out.print("endpoint");
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

    //expose oauth/check_token
    @Override
    public void configure(AuthorizationServerSecurityConfigurer auth) throws Exception{
        auth.checkTokenAccess("isAuthenticated()");
    }

}
