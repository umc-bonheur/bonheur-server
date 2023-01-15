package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.MemberSocialType;

public interface MemberRepositoryCustom {
    boolean existMemberBySocialInfo(String socialId, MemberSocialType socialType);
}
