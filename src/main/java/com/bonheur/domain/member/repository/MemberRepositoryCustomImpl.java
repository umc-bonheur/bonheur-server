package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.*;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.DateTimeOperation;
import com.querydsl.core.types.dsl.NumberOperation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bonheur.domain.board.model.QBoard.board;
import static com.bonheur.domain.boardtag.model.QBoardTag.boardTag;
import static com.bonheur.domain.member.model.QMember.member;
import static com.bonheur.domain.tag.model.QTag.tag;
import static com.querydsl.core.types.Projections.*;
import static com.querydsl.core.types.dsl.Expressions.dateTimeOperation;
import static com.querydsl.core.types.dsl.Expressions.numberOperation;
import static java.util.Objects.isNull;

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
    public FindAllActiveResponse findCountHappyAndCountTag(Long memberId) {
        return queryFactory
                .select(fields(FindAllActiveResponse.class,
                        board.countDistinct().as("countHappy"),
                        boardTag.tag.name.countDistinct().as("countTag")
                        ))
                .from(boardTag, board)
                .where(boardTag.board.member.id.eq(memberId),
                        board.member.id.eq(memberId))
                .distinct()
                .fetchFirst();
    }

    @Override
    public Long findRecordDay(Long memberId){
        DateTimeOperation<Integer> toDate = dateTimeOperation(Integer.class, Ops.DateTimeOps.DATE, board.createdAt);
        return queryFactory
                .select(toDate.countDistinct())
                .from(board)
                .where(board.member.id.eq(memberId))
                .groupBy(toDate)
                .fetchFirst();
    }

    @Override
    public List<FindByTagResponse> findByTag(Long memberId) {
        return queryFactory
                .select(fields(FindByTagResponse.class,
                        tag.name.as("tagName"),
                        tag.name.count().as("countTag")
                        ))
                .from(boardTag)
                .join(boardTag.board, board)
                .join(boardTag.tag, tag)
                .where(board.member.id.eq(memberId))
                .groupBy(tag.name)
                .orderBy(tag.name.count().desc())
                .limit(5)
                .distinct()
                .fetch();
    }

    @Override
    public Long findByTime(Long memberId, int start, int end) {
        NumberOperation<Integer> toTime = numberOperation(Integer.class, Ops.DateTimeOps.HOUR, board.createdAt);

        Long result = queryFactory
                .select(toTime.count())
                .from(board)
                .where(board.member.id.eq(memberId),
                        toTime.between(start, end))
                .groupBy(toTime)
                .distinct()
                .fetchFirst();

        return isNull(result) ? 0 : result;
    }

    @Override
    public Long findNightTime(Long memberId) {
        NumberOperation<Integer> toTime = numberOperation(Integer.class, Ops.DateTimeOps.HOUR, board.createdAt);

        Long result = queryFactory
                .select(toTime.count())
                .from(board)
                .where(board.member.id.eq(memberId),
                        toTime.between(20, 25).or(toTime.between(0,1)))
                .groupBy(toTime)
                .distinct()
                .fetchFirst();

        return isNull(result) ? 0 : result;
    }

    @Override
    public List<FindByDayResponse> findByDay(Long memberId){
        NumberOperation<Integer> toDay = numberOperation(Integer.class, Ops.DateTimeOps.DAY_OF_WEEK, board.createdAt);

        return queryFactory
                .select(fields(FindByDayResponse.class,
                                        toDay.when(1).then("sun")
                                                .when(2).then("mon")
                                                .when(3).then("tue")
                                                .when(4).then("wed")
                                                .when(5).then("thr")
                                                .when(6).then("fri")
                                                .when(7).then("sat")
                                                .otherwise("기타").max().as("dayOfWeek"),
                                        toDay.count().as("countDay")
                        ))
                .from(board)
                .where(board.member.id.eq(memberId))
                .groupBy(toDay)
                .distinct()
                .fetch();
    }

    @Override
    public List<FindByMonthResponse> findByMonth(Long memberId) {
        NumberOperation<Integer> toMonth = numberOperation(Integer.class, Ops.DateTimeOps.MONTH, board.createdAt);

        return queryFactory
                .select(fields(FindByMonthResponse.class,
                        toMonth.when(1).then("jan")
                                .when(2).then("feb")
                                .when(3).then("mar")
                                .when(4).then("apr")
                                .when(5).then("may")
                                .when(6).then("jun")
                                .when(7).then("jul")
                                .when(8).then("aug")
                                .when(9).then("sept")
                                .when(10).then("oct")
                                .when(11).then("nov")
                                .when(12).then("dec")
                                .otherwise("기타").max().as("month"),
                        toMonth.count().as("countMonth")
                ))
                .from(board)
                .where(board.member.id.eq(memberId))
                .groupBy(toMonth)
                .distinct()
                .fetch();
    }

}

