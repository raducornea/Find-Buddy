package com.example.third.config.security.filters;

import com.example.third.config.security.authentication.CustomAuthentication;
import com.example.third.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Custom Filter (First) // Manager (Second) // Provider (Third) - can use UDS (User Details Service) & PE (Password Encoder)
@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    // Second
    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. create an authentication object which is not yet authenticated
        String key = String.valueOf(request.getHeader("key"));
        CustomAuthentication ca = new CustomAuthentication(false, key);

        // 2. delegate the authentication object to the manager
        // 3. get back the authentication from the manager
        var a = customAuthenticationManager.authenticate(ca);

        // 4. if the object is authenticated then send request to the next filter in the chain
        if (a.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(a);

            // propagate to the next filter in the filter chain
            // only when the authentication works
            filterChain.doFilter(request, response);
        }
    }
}
