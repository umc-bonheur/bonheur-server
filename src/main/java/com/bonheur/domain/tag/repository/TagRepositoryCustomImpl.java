package com.bonheur.domain.tag.repository;

import com.bonheur.domain.tag.model.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bonheur.domain.tag.model.QTag.tag;
import static com.bonheur.domain.membertag.model.QMemberTag.memberTag;

@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long findOwnTagByTagName(Long memberId, String tagName) {
        return queryFactory.select(tag.id).from(tag, memberTag)
                .where(
                        // member에 해당되는 태그
                        memberTag.member.id.eq(memberId),
                        // tagName에 해당되는 태그
                        tag.name.eq(tagName)
                ).join(tag.memberTags, memberTag).fetchOne();
    }
    // 생각한 쿼리 : select tag.id from tag, member_tag where tag.id=tag_id and member_tag.member_id=1 and tag.name='tag1';

    @Override
    public Tag findOwnTagByTagId(Long memberId, Long tagId) {
        return queryFactory.select(tag)
                .from(tag)
                .join(tag.memberTags, memberTag)
                .where(
                        memberTag.member.id.eq(memberId),
                        tag.id.eq(tagId)
                ).fetchOne();
    }

    @Override
    public Long isExistTag(Long memberId, Long tagId) {
        return queryFactory.select(tag.count()).from(tag, memberTag)
                .where(
                        // member에 해당되는 태그
                        memberTag.member.id.eq(memberId),
                        // tagName에 해당되는 태그
                        tag.id.eq(tagId)
                ).join(tag.memberTags, memberTag).fetchOne();
    }

    @Override
    public List<Tag> getTagUsedByMember(Long memberId) {
        return queryFactory.select(tag)
                .from(tag)
                .leftJoin(tag.memberTags,memberTag).fetchJoin()
                .leftJoin(memberTag.member).fetchJoin()
                .where(
                        memberTag.member.id.eq(memberId)
                )
                .distinct()
                .orderBy(memberTag.updatedAt.desc())
                .offset(0)
                .limit(5)
                .fetch();
    }
}
