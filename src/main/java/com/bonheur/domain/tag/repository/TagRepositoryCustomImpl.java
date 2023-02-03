package com.bonheur.domain.tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.bonheur.domain.tag.model.QTag.tag;
import static com.bonheur.domain.membertag.model.QMemberTag.memberTag;

@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long findOwnTagByTagName(Long memberId, String tagName) {
        return queryFactory.select(tag.id).from(tag)
                .where(
                        // member에 해당되는 태그
                        memberTag.member.id.eq(memberId),
                        // tagName에 해당되는 태그
                        tag.name.eq(tagName)
                ).join(memberTag).fetchOne();
    }
}
