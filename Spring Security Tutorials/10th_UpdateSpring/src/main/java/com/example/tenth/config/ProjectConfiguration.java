package com.example.tenth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
//@EnableGlobalMethodSecurity // deprecated
@EnableWebSecurity // pentru a nu mai adauga @PreAuthorize("")
public class ProjectConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/demo")
            .access(new WebExpressionAuthorizationManager("isAuthenticated()"));
//            .authenticated()

        return http.build();
    }


}
