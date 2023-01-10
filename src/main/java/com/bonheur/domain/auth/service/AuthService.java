package com.bonheur.domain.auth.service;

import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;

public interface AuthService {
    Long signUp(SocialSignUpRequest request);
}
