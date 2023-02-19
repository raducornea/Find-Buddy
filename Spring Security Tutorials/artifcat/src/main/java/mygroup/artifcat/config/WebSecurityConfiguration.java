package mygroup.artifcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Component that manages user details
 * - where to get credentials froms
 * -
 */
@Configuration
public class WebSecurityConfiguration {
    // can be named anything lol

    // contractul lui Spring Security, prin care Spring intelege cum sunt transmise datele de utilizator
    // astfel, nu va mai fi folosit cel cu parolele random la pornirea aplicatiei
    @Bean
    public UserDetailsService userDetailsService() {
        // easy
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("bill")
                .password("12345")
                .authorities("read") // mandator sa aiba autoritate (rol/actiune)
                .build();

        uds.createUser(u1);
        return uds;
    }

    // for managing passwords -> passwords encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        // use other passwords encoders like:
        // BCryptPasswordEncoder()
        return NoOpPasswordEncoder.getInstance();
    }
}
