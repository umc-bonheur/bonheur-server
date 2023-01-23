package com.bonheur.domain.membertag.service;

import com.bonheur.domain.member.model.Member;

import java.util.List;

public interface MemberTagService {
    void createMemberTags(Member member, List<Long> tagIds);
}