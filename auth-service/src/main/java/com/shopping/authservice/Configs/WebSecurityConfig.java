package com.shopping.authservice.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableGlobalMethodSecurity(securedEnabled = true) @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder());
//    }

// oauth 2.0 , when client try to access resource server, client will request authorize code from authorize service,
// authorize server will redirect to user login/authentication  (AuthenticationManagerBuilder auth handle this)
// after user authentication granted, author server will response client the authorization code, ( ClientDetailsServiceConfigurer clients handle this)
//  then client use clientid+secret and the authorization code to get access token (AuthorizationServerEndpointsConfigurer endpoints handle this)

// https://www.baeldung.com/role-and-privilege-for-spring-security-registration
// https://github.com/Baeldung/spring-security-registration/blob/992875b141f765652cb89e3c331818ad60277127/src/main/java/com/baeldung/spring/SecSecurityConfig.java
// https://www.baeldung.com/spring-security-two-factor-authentication-with-soft-token
// https://www.baeldung.com/java-config-spring-security
// https://www.devglan.com/spring-security/spring-oauth2-role-based-authorization
// https://www.baeldung.com/role-and-privilege-for-spring-security-registration


//architecture
// https://auth0.com/docs/flows/concepts/auth-code
// https://medium.com/takeaway-tech/wiring-your-own-authentication-with-springbootoauth2-a636b7420714
// https://spring.io/guides/topicals/spring-security-architecture
// https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/htmlsingle/

//QA
//// https://stackoverflow.com/questions/22998731/httpsecurity-websecurity-and-authenticationmanagerbuilder

//customize userdetailsservice https://www.boraji.com/spring-security-5-custom-userdetailsservice-example
// userservice vs authenticationprovider https://pattern-match.com/blog/2018/10/17/springboot2-with-oauth2-integration/

//jwt https://www.baeldung.com/spring-security-oauth-jwt
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;



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
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
