package com.example.fourth.config.security.managers;

import com.example.fourth.config.security.providers.APIKeyProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var provider = new APIKeyProvider(key);
        if (provider.supports(authentication.getClass())) {
            return provider.authenticate(authentication);
        }

        return authentication;
    }
}
