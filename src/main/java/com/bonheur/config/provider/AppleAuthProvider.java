package com.bonheur.config.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppleAuthProvider implements AuthProvider{
    @Override
    public String getSocialId(String token) {
        return null;
    }
}
