package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.*;
import com.bonheur.domain.tag.model.Tag;

import java.util.List;

import javax.validation.constraints.NotNull;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    Member findMemberBySocialInfo(@NotNull String socialId, @NotNull MemberSocialType socialType);
    FindActiveRecordResponse findCountHappyAndCountTagByMemberId(Long memberId);
    List<FindTagRecordResponse> findTagRecordByMemberId(Long memberId);
    Long findTimeRecordByMemberId(Long memberId, int start, int end);
    Long findNightTimeRecordByMemberId(Long memberId);
    List<FindDayRecordResponse> findDayRecordByMemberId(Long memberId);
    List<FindMonthRecordResponse> findMonthRecordByMemberId(Long memberId);
}
