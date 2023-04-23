package com.project.profile.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
                .requestMatchers(HttpMethod.POST, "/profile/new-profile").access(WebExpressionAuthorizationManager("hasIpAddress('localhost')"))
                .requestMatchers("/discovery/**").access(WebExpressionAuthorizationManager("hasIpAddress('localhost')"))
                .anyRequest().permitAll()
                .and()

            .csrf().disable()
            .cors().disable()
            .build()
    }
}