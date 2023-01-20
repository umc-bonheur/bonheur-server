package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;
import com.bonheur.domain.member.model.dto.FindByTimeResponse;

import java.util.List;

public interface MemberService {
    Long registerMember(CreateMemberRequest request);
    FindAllMonthlyResponse findAllMonthly(Long memberId);

    List<FindByTagResponse> findByTag(Long memberId);
    FindByTimeResponse findByTime(Long memberId);
}
