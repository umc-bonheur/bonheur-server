package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(String socialId, MemberSocialType socialType);
    FindAllMonthlyResponse findAllMonthly(Long memberId);
}
