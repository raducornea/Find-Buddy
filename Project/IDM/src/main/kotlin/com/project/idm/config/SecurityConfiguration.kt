package com.project.idm.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Component that manages user details
 * - where to get credentials froms
 */
@Configuration
class SecurityConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // use other passwords encoders like:
        // BCryptPasswordEncoder()
        return NoOpPasswordEncoder.getInstance()
    }

}