package com.shopping.authservice.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// oauth 2.0 , when client try to access resource server, client will request authorize code from authorize service,
// authorize server will redirect to user login/authentication  (AuthenticationManagerBuilder auth handle this)
// after user authentication granted, author server will response client the authorization code, ( ClientDetailsServiceConfigurer clients handle this)
//  then client use clientid+secret and the authorization code to get access token (AuthorizationServerEndpointsConfigurer endpoints handle this)
// https://auth0.com/docs/flows/concepts/auth-code
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("doctor").password(getEncoder() .encode("doctor")).roles("ADMIN");
        auth.inMemoryAuthentication().withUser("nurse").password(getEncoder() .encode("nurse")).roles("USER");
    }
}
