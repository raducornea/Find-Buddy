package com.example.fifth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// endpoint security - for web apps + through filter chain
// methods security - for bean methods + through aspects
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // what you can put in anyRequest + authenticated
        // matcher methods + authorization rule
        // 1. which matcher methods should you use and how (anyRequest(), mvcMatchers(), antMatchers(), regexMatchers())
        // 2. how to apply different authorization rules

        return http.httpBasic()
            .and().authorizeHttpRequests()

//                .anyRequest().authenticated() // endpoint level authorization
//                .anyRequest().permitAll() // no authentication BUT using wrong authentication fails!
//                .anyRequest().denyAll() // XD - banned?
//                .anyRequest().hasAuthority("read") // 401 Unauthorized (Authentication fails) | 403 Forbidden (Authorization fails)
//                .anyRequest().hasRole("ADMIN")
//                .anyRequest().hasAnyRole("ADMIN", "MANAGER")
//                .anyRequest().access("isAuthenticated() and hasAuthority('read')") // SpEL --> authorization rules
//                .requestMatchers(HttpMethod.GET, "/demo").hasAuthority("read") // cu metoda???
                .requestMatchers("/demo").hasAuthority("read")
//                .requestMatchers("/api/**").hasAuthority("write") // all routes who have the parent /api
//                .and().csrf().disable() // DON'T DO THIS IN REAL WORLD APPS // protection against attacks for CUD ops.
                .anyRequest().authenticated()
                .and().build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
//                .roles("ADMIN") // == .authorities("ROLE_ADMIN")
                .build();
        uds.createUser(u1);

        var u2 = User.withUsername("john")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();
        uds.createUser(u2);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
