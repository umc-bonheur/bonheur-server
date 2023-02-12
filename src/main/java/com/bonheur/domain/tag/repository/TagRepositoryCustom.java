package com.bonheur.domain.tag.repository;

import com.bonheur.domain.tag.model.Tag;

public interface TagRepositoryCustom {
    Long findOwnTagByTagName(Long memberId, String tagName);
    Tag findOwnTagByTagId(Long memberId, Long tagId);
    Long isExistTag(Long memberId, Long tagId);
}
