package com.bonheur.domain.membertag.service;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.tag.model.Tag;

import java.util.List;

public interface MemberTagService {
    void createMemberTags(Member member, List<Tag> tags);
}