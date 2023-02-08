package com.bonheur.domain.tag.repository;

import com.bonheur.domain.tag.model.Tag;

public interface TagRepositoryCustom {
    Long findOwnTagByTagName(Long memberId, String tagName);
    Tag findOwnTagByMemberId(Long memberId, Long tagId);
}
