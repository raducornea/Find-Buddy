package com.project.idm.config

//import com.project.idm.config.security.filters.CustomAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


/**
 * Component that manages user details
 * - where to get credentials froms
 */
@Configuration
//@EnableWebSecurity
//class SecurityConfiguration(private val customAuthenticationFilter: CustomAuthenticationFilter) {
class SecurityConfiguration() {

//    @Bean
//    fun userDetailsService(): UserDetailsService {
//        val uds = InMemoryUserDetailsManager()
//
//        val u1 = User.withUsername("bill")
//            .password(passwordEncoder().encode("12345"))
//            .authorities("read") //                .roles("ADMIN") // == .authorities("ROLE_ADMIN")
//            .build()
//        uds.createUser(u1)
//
//        val u2 = User.withUsername("john")
//            .password(passwordEncoder().encode("12345"))
//            .authorities("write")
//            .build()
//        uds.createUser(u2)
//
//        return uds
//    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        // what you can put in anyRequest + authenticated
        // matcher methods + authorization rule
        // 1. which matcher methods should you use and how (anyRequest(), mvcMatchers(), antMatchers(), regexMatchers())
        // 2. how to apply different authorization rules
        return http.httpBasic()
//            .addFilterAt(this.customAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .and().authorizeHttpRequests().anyRequest().authenticated() // don't worry about this?
            .and().authorizeHttpRequests().requestMatchers("/demo").hasAuthority("read")
            .and().authorizeHttpRequests().requestMatchers("/hello").hasAuthority("write")
            .anyRequest().authenticated()
            .and().build()
    }

//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return NoOpPasswordEncoder.getInstance()
//    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {

//        val passwordEncoder = BCryptPasswordEncoder()
//        val password = "12345"
//        val hashedPassword = passwordEncoder.encode(password)
//        println(hashedPassword)

        return BCryptPasswordEncoder()
    }


}