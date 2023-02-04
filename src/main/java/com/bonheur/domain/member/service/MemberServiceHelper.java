package com.bonheur.domain.member.service;

import com.bonheur.domain.common.exception.ConflictException;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E409_DUPLICATE_MEMBER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceHelper {
    static void validateNotExistsUser(MemberRepository memberRepository, String socialId, MemberSocialType socialType) {
        if (memberRepository.existMemberBySocialInfo(socialId, socialType)) {
            throw new ConflictException(String.format("이미 가입한 회원(%s - %s) 입니다", socialId, socialType), E409_DUPLICATE_MEMBER);
        }
    }
}
