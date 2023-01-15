package com.bonheur.config.provider;

public interface AuthProvider {
    String getSocialId(String token);
}
