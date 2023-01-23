package com.bonheur.domain.auth.service;

import com.bonheur.domain.auth.model.dto.LoginRequest;
import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;

public interface AuthService {
    Long signUp(SocialSignUpRequest request);

    Long login(LoginRequest request);

    void withdrawal(Long memberId);
}
