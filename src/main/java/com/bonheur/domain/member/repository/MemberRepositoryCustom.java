package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.*;

import java.util.List;

import javax.validation.constraints.NotNull;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    Member findMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    FindAllActiveResponse findCountHappyAndCountTag(Long memberId);
    Long findRecordDay(Long memberId);
    List<FindByTagResponse> findByTag(Long memberId);
    Long findByTime(Long memberId, int start, int end);
    Long findNightTime(Long memberId);
    List<FindByDayResponse> findByDay(Long memberId);
    List<FindByMonthResponse> findByMonth(Long memberId);


}
