package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllActiveResponse;
import com.bonheur.domain.member.model.dto.FindByDayResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;

import java.util.List;

import javax.validation.constraints.NotNull;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    Member findMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    FindAllActiveResponse findAllActive(Long memberId);
    List<FindByTagResponse> findByTag(Long memberId);
    Long findByTime(Long memberId, String start, String end);
    Long findNightTime(Long memberId);
    List<FindByDayResponse> findByDay(Long memberId);
    Long findByMonth(Long memberId, String month);


}
