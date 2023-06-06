package com.example.pieknarzecz.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity (securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder.encode("123456")).roles("USER1");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
