package com.bonheur.domain.auth.controller;

import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;
import com.bonheur.domain.auth.model.dto.SocialSignUpResponse;
import com.bonheur.domain.auth.service.AuthService;
import com.bonheur.domain.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final HttpSession httpSession;

    private final AuthService authService;

    @PostMapping("/v1/social-signup")
    public ApiResponse<SocialSignUpResponse> signUp(
            @Valid @RequestBody SocialSignUpRequest request
    ) {
        Long memberId = authService.signUp(request);
        httpSession.setAttribute("MEMBER_ID", memberId);

        SocialSignUpResponse response = SocialSignUpResponse.of(httpSession.getId(), memberId);
        return ApiResponse.success(response);
    }
}
