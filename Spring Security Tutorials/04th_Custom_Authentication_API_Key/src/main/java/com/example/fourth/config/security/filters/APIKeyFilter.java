package com.example.fourth.config.security.filters;

import com.example.fourth.config.security.authentications.APIKeyAuthentication;
import com.example.fourth.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class APIKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CustomAuthenticationManager manager = new CustomAuthenticationManager(key);

        // api key filtering
        var requestKey = request.getHeader("x-api-key");
        if (requestKey == null || "null".equals(requestKey)) {
            filterChain.doFilter(request, response);
        }

        // basic auth filtering
        var auth = new APIKeyAuthentication(requestKey);
        try {
            var a = manager.authenticate(auth);

            if (a.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(a);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
