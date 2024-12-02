package com.online_restaurant.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class PassWordEncoderConfig {

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder(16);
    }
}
