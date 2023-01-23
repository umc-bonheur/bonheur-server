package com.bonheur.domain.member.controller;


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
    public ApiResponse<UpdateMemberProfileResponse> updateProfile(
            @RequestPart MultipartFile image,
            @RequestPart @Valid UpdateMemberProfileRequest updateMemberProfileRequest) throws IOException {

        Long memberId = 1L; //session 관련 검증 추가해야 함!

        return ApiResponse.success(memberService.updateMemberProfile(memberId, updateMemberProfileRequest, image));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 활동 종합 조회")
    @GetMapping("/api/mypages")
    public ApiResponse<FindAllActiveResponse> findAllActive() {
        Long memberId = 1L; // 임시 로그인한 유저 id
        return ApiResponse.success(memberService.findAllActive(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 태그별 조회")
    @GetMapping("/api/mypages/tag")
    public ApiResponse<List<FindByTagResponse>> findByTag() {
        Long memberId = 1L; // 임시 로그인한 유저 id
        return ApiResponse.success(memberService.findByTag(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 시간별 조회")
    @GetMapping("/api/mypages/time")
    public ApiResponse<FindByTimeResponse> findByTime() {
        Long memberId = 1L; // 임시 로그인한 유저 id
        return ApiResponse.success(memberService.findByTime(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 요일 조회")
    @GetMapping("/api/mypages/day")
    public ApiResponse<List<FindByDayResponse>> findByDay() {
        Long memberId = 1L; // 임시 로그인한 유저 id
        return ApiResponse.success(memberService.findByDay(memberId));
    }

    @ApiDocumentResponse
    @Operation(summary = "마이페이지 통계 - 월별 조회")
    @GetMapping("/api/mypages/month")
    public ApiResponse<FindByMonthResponse> findByMonth() {
        Long memberId = 1L; // 임시 로그인한 유저 id
        return ApiResponse.success(memberService.findByMonth(memberId));
    }

}
