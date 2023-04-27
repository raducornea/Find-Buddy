package com.project.idm.config

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


/**
 * Component that manages user details
 * - where to get credentials froms
 */
@Configuration
@EnableWebSecurity
class SecurityConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .authorizeHttpRequests().requestMatchers("/**").permitAll().and().csrf().disable().build()

//            .authorizeHttpRequests()
//                .requestMatchers("/invalidate-token").access(WebExpressionAuthorizationManager("hasIpAddress('localhost')"))
//                .requestMatchers("/verify-token").access(WebExpressionAuthorizationManager("hasIpAddress('localhost')"))
//
//                .requestMatchers("/register-temporary").permitAll()
//                .requestMatchers("/check-username/**").permitAll()
//                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                .requestMatchers("/register").permitAll()
//                .requestMatchers("/**").hasAnyAuthority("read", "write")
//                .anyRequest().authenticated()
//                .and()
//
//            .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//
//            .logout()
//                .logoutSuccessUrl("/login")
//                .permitAll()
//                .and()
//
//            .cors().disable()
//            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}