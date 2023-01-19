package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bonheur.domain.board.model.QBoard.board;
import static com.bonheur.domain.boardtag.model.QBoardTag.boardTag;
import static com.bonheur.domain.member.model.QMember.member;
import static com.bonheur.domain.tag.model.QTag.tag;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existMemberBySocialInfo(String socialId, MemberSocialType socialType) {
        return queryFactory
                .selectOne()
                .from(member)
                .where(
                        member.memberSocialInfo.socialId.eq(socialId),
                        member.memberSocialInfo.socialType.eq(socialType)
                ).fetchFirst() != null;
    }

    @Override
    public Member findMemberBySocialInfo(String socialId, MemberSocialType socialType) {
        return queryFactory
                .selectFrom(member)
                .where(
                        member.memberSocialInfo.socialId.eq(socialId),
                        member.memberSocialInfo.socialType.eq(socialType)
                ).fetchFirst();
    }

    @Override
    public FindAllMonthlyResponse findAllMonthly(Long memberId) {

        // todo : 마이페이지 통계 - 며칠동안 행복을 기록했는 지 조회 추가하기
        // StringTemplate toDate = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", board.createdAt, ConstantImpl.create("%Y-%m-%d"));

        return queryFactory
                .select(Projections.fields(FindAllMonthlyResponse.class,
                        board.countDistinct().as("countHappy"),
                        boardTag.countDistinct().as("countHashtag")
                        ))
                .from(boardTag, member)
                .join(boardTag.board, board)
                .where( member.id.eq(memberId),
                        board.member.eq(member))
                .distinct()
                .fetchFirst();
    }

    @Override
    public List<FindByTagResponse> findByTag(Long memberId) {
        return queryFactory
                .select(Projections.fields(FindByTagResponse.class,
                        tag.name.as("tagName"),
                        tag.name.count().as("countTag")
                        ))
                .from(boardTag, member)
                .join(boardTag.board, board)
                .join(boardTag.tag, tag)
                .where(member.id.eq(memberId),
                        board.member.eq(member))
                .groupBy(tag.name)
                .orderBy(tag.name.count().desc())
                .limit(5)
                .distinct()
                .fetch();
    }

}

