package com.bonheur.domain.tag.repository;

public interface TagRepositoryCustom {
    Long findOwnTagByTagName(Long memberId, String tagName);
}
