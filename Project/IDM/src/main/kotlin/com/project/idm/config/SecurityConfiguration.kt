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


/**
 * Component that manages user details
 * - where to get credentials froms
 */
@Configuration
@EnableWebSecurity
class SecurityConfiguration : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http

            .authorizeHttpRequests()
                .requestMatchers("/verify-token", "/check-username/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/invalidate-token").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/**").hasAnyAuthority("read", "write")
                .anyRequest().authenticated()
                .and()

            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()

            .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()

            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}