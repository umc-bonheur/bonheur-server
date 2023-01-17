package com.bonheur.domain.auth.controller;

import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;
import com.bonheur.domain.auth.model.dto.SocialSignUpResponse;
import com.bonheur.domain.auth.service.AuthService;
import com.bonheur.domain.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final HttpSession httpSession;

    private final AuthService authService;

    @ApiDocumentResponse
    @Operation(summary = "소셜 회원가입")
    // 이상 Swagger 코드
    @PostMapping("/auth/social-signup")
    public ApiResponse<SocialSignUpResponse> signUp(
            @Valid @RequestBody SocialSignUpRequest request
    ) {
        Long memberId = authService.signUp(request);
        httpSession.setAttribute("MEMBER_ID", memberId);

        SocialSignUpResponse response = SocialSignUpResponse.of(httpSession.getId(), memberId);
        return ApiResponse.success(response);
    }
}
