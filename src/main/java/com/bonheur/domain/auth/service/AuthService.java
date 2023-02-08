package com.bonheur.domain.auth.service;

import com.bonheur.domain.auth.model.dto.LoginRequest;
import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    Long signUp(SocialSignUpRequest request, MultipartFile profileImage) throws IOException;

    Long login(LoginRequest request);

    void withdrawal(Long memberId);
}
