package com.bonheur.domain.tag.repository;

import com.bonheur.domain.tag.model.Tag;

import java.util.List;

public interface TagRepositoryCustom {
    Long findOwnTagByTagName(Long memberId, String tagName);
    Tag findOwnTagByTagId(Long memberId, Long tagId);
    Long isExistTag(Long memberId, Long tagId);
    List<Tag> getTagUsedByMember(Long memberId);
}
