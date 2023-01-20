package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;
import com.bonheur.domain.member.model.dto.FindByTimeResponse;

import java.util.List;

import javax.validation.constraints.NotNull;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    Member findMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    FindAllMonthlyResponse findAllMonthly(Long memberId);
    List<FindByTagResponse> findByTag(Long memberId);
    FindByTimeResponse findByTime(Long memberId);
}
