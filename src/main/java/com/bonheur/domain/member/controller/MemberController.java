package com.bonheur.domain.member.controller;

import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.member.model.dto.UpdateMemberProfileRequest;
import com.bonheur.domain.member.model.dto.UpdateMemberProfileResponse;
import com.bonheur.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @ApiDocumentResponse
    @Operation(summary = "프로필 수정")
    @PatchMapping("/api/member/profiles")
    public ApiResponse<UpdateMemberProfileResponse> updateProfile(
            @RequestPart MultipartFile image,
            @RequestPart UpdateMemberProfileRequest updateMemberProfileRequest) throws IOException {

        Long memberId = 1L; //session 관련 검증 추가해야 함!

        return ApiResponse.success(memberService.updateMemberProfile(memberId, updateMemberProfileRequest, image));
    }
}
