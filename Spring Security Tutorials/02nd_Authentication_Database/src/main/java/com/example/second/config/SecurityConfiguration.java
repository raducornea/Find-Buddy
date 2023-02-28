package com.example.second.config;

import com.example.second.services.JPAUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {

    @Bean
    // definition of password encoder
    public PasswordEncoder passwordEncoder() {
        // replace with BCryptPasswordEncoder
        return NoOpPasswordEncoder.getInstance();
    }
}
