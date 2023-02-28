package com.example.fourth.config.security.providers;

import com.example.fourth.config.security.authentications.APIKeyAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class APIKeyProvider implements AuthenticationProvider {

    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        APIKeyAuthentication auth = (APIKeyAuthentication) authentication;
        if (key.equals(auth.getKey())) {
            auth.setAuthenticated(true);
            return auth;
        }

        throw new BadCredentialsException(":(");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return APIKeyAuthentication.class.equals(authentication);
    }
}
