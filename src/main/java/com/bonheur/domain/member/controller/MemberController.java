package com.bonheur.domain.member.controller;


import com.bonheur.config.interceptor.Auth;
import com.bonheur.config.resolver.MemberId;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.member.model.dto.*;
import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.Valid;
import java.io.IOException;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @ApiDocumentResponse
    @Operation(summary = "프로필 수정")
    @PatchMapping("/api/member/profiles")
    @Auth
    public ApiResponse<UpdateMemberProfileResponse> updateProfile(
            @RequestPart(required = false) MultipartFile image,
            @RequestPart @Valid UpdateMemberProfileRequest updateMemberProfileRequest,
            @Valid @MemberId Long memberId) throws IOException {
        return ApiResponse.success(memberService.updateMemberProfile(memberId, updateMemberProfileRequest, image));
    }

    @ApiDocumentResponse
    @Operation(summary = "프로필 조회")
    @GetMapping("/api/member/profiles")
    @Auth
    public ApiResponse<GetMemberProfileResponse> getMemberProfile(@Valid @MemberId Long memberId) {
        return ApiResponse.success(memberService.getMemberProfile(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 활동 종합 조회")
    @GetMapping("/api/mypages")
    @Auth
    public ApiResponse<FindActiveRecordResponse> findMyActiveRecord(@Valid @MemberId Long memberId) {
        return ApiResponse.success(memberService.findMyActiveRecord(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 태그별 조회")
    @GetMapping("/api/mypages/tag")
    @Auth
    public ApiResponse<List<FindTagRecordResponse>> findMyTagRecord(@Valid @MemberId Long memberId) {
        return ApiResponse.success(memberService.findMyTagRecord(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 시간별 조회")
    @GetMapping("/api/mypages/time")
    @Auth
    public ApiResponse<List<FindTimeRecordResponse>> findMyTimeRecord(@Valid @MemberId Long memberId) {
        return ApiResponse.success(memberService.findMyTimeRecord(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 요일 조회")
    @GetMapping("/api/mypages/day")
    @Auth
    public ApiResponse<List<FindDayRecordResponse>> findMyDayRecord(@Valid @MemberId Long memberId) {
        return ApiResponse.success(memberService.findMyDayRecord(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 월별 조회")
    @GetMapping("/api/mypages/month")
    @Auth
    public ApiResponse<List<FindMonthRecordResponse>> findMyMonthRecord(@Valid @MemberId Long memberId) {
        return ApiResponse.success(memberService.findMyMonthRecord(memberId));
    }
}
