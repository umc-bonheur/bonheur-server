package com.bonheur.domain.member.controller;

import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.member.model.dto.CreateMemberProfileRequest;
import com.bonheur.domain.member.model.dto.CreateMemberProfileResponse;
import com.bonheur.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/member/profiles")
    public ApiResponse<CreateMemberProfileResponse> createBoard(
            @RequestPart MultipartFile image,
            @RequestPart CreateMemberProfileRequest createMemberProfileRequest) throws IOException {

        Long memberId = 1L; //session 관련 검증 추가해야 함!

        return ApiResponse.success(memberService.createMemberProfile(memberId, createMemberProfileRequest, image));
    }
}
