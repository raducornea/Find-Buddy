package com.project.profile.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager


@Configuration
@EnableWebSecurity
class SecurityConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http

            .authorizeHttpRequests()
                .requestMatchers("/profile/**").access(
                    WebExpressionAuthorizationManager("isAnonymous() and hasIpAddress('localhost')"))
                .anyRequest().permitAll()
                .and()

            .cors().disable()
            .build()
    }
}