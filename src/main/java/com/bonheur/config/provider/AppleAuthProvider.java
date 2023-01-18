package com.bonheur.config.provider;

import com.bonheur.config.client.apple.AppleTokenDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppleAuthProvider implements AuthProvider{
    private final AppleTokenDecoder appleTokenDecoder;
    @Override
    public String getSocialId(String token) {
        return appleTokenDecoder.getSocialIdFromToken(token);
    }
}
