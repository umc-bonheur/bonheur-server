package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.dto.CreateMemberRequest;

public interface MemberService {
    Long registerMember(CreateMemberRequest request);
}
