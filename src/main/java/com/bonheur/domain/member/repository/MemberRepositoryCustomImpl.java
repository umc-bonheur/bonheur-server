package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllMonthlyResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.bonheur.domain.board.model.QBoard.board;
import static com.bonheur.domain.boardtag.model.QBoardTag.boardTag;
import static com.bonheur.domain.member.model.QMember.member;

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
    public FindAllMonthlyResponse findAllMonthly(Long memberId) {
        return queryFactory
                .select(Projections.fields(FindAllMonthlyResponse.class,
                        null,
                        board.id.count().as("countHappy"),
                        boardTag.id.count().as("countHashtag"),
                        board.createdAt.count().as("countRecordDay")))
                .from(member)
                .where(member.id.eq(memberId),
                        member.eq(board.member),
                        board.eq(boardTag.board))
                .distinct()
                .fetchFirst();
    }

}
