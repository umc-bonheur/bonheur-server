package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;

import java.util.List;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(String socialId, MemberSocialType socialType);
    FindAllMonthlyResponse findAllMonthly(Long memberId);
    List<FindByTagResponse> findByTag(Long memberId);
}
