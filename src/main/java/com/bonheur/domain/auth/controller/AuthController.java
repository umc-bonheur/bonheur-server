package com.bonheur.domain.auth.controller;

import com.bonheur.config.interceptor.Auth;
import com.bonheur.config.resolver.MemberId;
import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.auth.model.dto.LoginRequest;
import com.bonheur.domain.auth.model.dto.LoginResponse;
import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;
import com.bonheur.domain.auth.model.dto.SocialSignUpResponse;
import com.bonheur.domain.auth.service.AuthService;
import com.bonheur.domain.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;

import static com.bonheur.config.session.SessionConstant.MEMBER_ID;

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
            @Valid @RequestPart SocialSignUpRequest socialSignUpRequest,
            @RequestPart(required = false) MultipartFile profileImage
    ) throws IOException {
        Long memberId = authService.signUp(socialSignUpRequest, profileImage);
        httpSession.setAttribute(MEMBER_ID, memberId);

        SocialSignUpResponse response = SocialSignUpResponse.of(httpSession.getId(), memberId);
        return ApiResponse.success(response);
    }

    @ApiDocumentResponse
    @Operation(summary = "로그인 요청")
    // 이상 Swagger 코드
    @PostMapping("/auth/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        Long memberId = authService.login(request);
        httpSession.setAttribute(MEMBER_ID, memberId);

        LoginResponse response = LoginResponse.of(httpSession.getId(), memberId);
        return ApiResponse.success(response);
    }

    @ApiDocumentResponse
    @Operation(summary = "로그아웃 요청")
    // 이상 Swagger 코드
    @Auth
    @PostMapping("/auth/logout")
    public ApiResponse<String> logout(
            @Valid @MemberId Long memberId
    ) {
        httpSession.invalidate();
        return ApiResponse.success("로그아웃이 되었습니다.");
    }

    @ApiDocumentResponse
    @Operation(summary = "회원 탈퇴 요청")
    // 이상 Swagger 코드
    @Auth
    @DeleteMapping("/auth/withdrawal")
    public ApiResponse<String> withdrawal(
            @Valid @MemberId Long memberId
    ) {
        authService.withdrawal(memberId);
        httpSession.invalidate();
        return ApiResponse.success("회원 탈퇴가 되었습니다.");
    }
}
