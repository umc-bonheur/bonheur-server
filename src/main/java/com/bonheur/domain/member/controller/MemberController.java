package com.bonheur.domain.member.controller;

import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.member.service.MemberService;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/mypages")
    public ApiResponse<FindAllMonthlyResponse> findAllMonthly() {
        Long memberId = 1L; // 임시 로그인한 유저 id
        FindAllMonthlyResponse response = memberService.findAllMonthly(memberId);

        return ApiResponse.success(response);
    }

}
