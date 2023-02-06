package com.bonheur.domain.member.service;

import com.bonheur.domain.common.exception.ConflictException;
import com.bonheur.domain.common.exception.NotFoundException;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E404_NOT_EXISTS_MEMBER;
import static com.bonheur.domain.common.exception.dto.ErrorCode.E409_DUPLICATE_MEMBER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceHelper {
    public static void validateNotExistsUser(MemberRepository memberRepository, String socialId, MemberSocialType socialType) {
        if (memberRepository.existMemberBySocialInfo(socialId, socialType)) {
            throw new ConflictException(String.format("이미 가입한 회원(%s - %s) 입니다", socialId, socialType), E409_DUPLICATE_MEMBER);
        }
    }

    public static Member getExistMember(MemberRepository memberRepository, Long memberId) {
        if (!memberRepository.existsById(memberId)){
            throw new NotFoundException("해당 id를 가진 회원이 존재하지 않습니다.", E404_NOT_EXISTS_MEMBER);
        }
        return memberRepository.findMemberById(memberId);
    }

    public static void validateMemberExists(MemberRepository memberRepository, Long memberId) {
        if (!memberRepository.existsById(memberId)){
            throw new NotFoundException("해당 id를 가진 회원이 존재하지 않습니다.", E404_NOT_EXISTS_MEMBER);
        }
    }
}
