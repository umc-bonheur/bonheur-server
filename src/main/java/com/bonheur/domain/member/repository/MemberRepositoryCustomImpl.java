package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.*;
import com.bonheur.domain.tag.model.Tag;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.NumberOperation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bonheur.domain.board.model.QBoard.board;
import static com.bonheur.domain.boardtag.model.QBoardTag.boardTag;
import static com.bonheur.domain.member.model.QMember.member;
import static com.bonheur.domain.membertag.model.QMemberTag.memberTag;
import static com.bonheur.domain.tag.model.QTag.tag;
import static com.querydsl.core.types.Projections.*;
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
    public FindActiveRecordResponse findCountHappyAndCountTagByMemberId(Long memberId) {
        return queryFactory
                .select(fields(FindActiveRecordResponse.class,
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
    public List<FindTagRecordResponse> findTagRecordByMemberId(Long memberId) {
        return queryFactory
                .select(fields(FindTagRecordResponse.class,
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
    public Long findTimeRecordByMemberId(Long memberId, int start, int end) {
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
    public Long findNightTimeRecordByMemberId(Long memberId) {
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
    public List<FindDayRecordResponse> findDayRecordByMemberId(Long memberId){
        NumberOperation<Integer> toDay = numberOperation(Integer.class, Ops.DateTimeOps.DAY_OF_WEEK, board.createdAt);

        return queryFactory
                .select(fields(FindDayRecordResponse.class,
                                        toDay.when(1).then("일")
                                                .when(2).then("월")
                                                .when(3).then("화")
                                                .when(4).then("수")
                                                .when(5).then("목")
                                                .when(6).then("금")
                                                .when(7).then("토")
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
    public List<FindMonthRecordResponse> findMonthRecordByMemberId(Long memberId) {
        NumberOperation<Integer> toMonth = numberOperation(Integer.class, Ops.DateTimeOps.MONTH, board.createdAt);

        return queryFactory
                .select(fields(FindMonthRecordResponse.class,
                        toMonth.when(1).then("01")
                                .when(2).then("02")
                                .when(3).then("03")
                                .when(4).then("04")
                                .when(5).then("05")
                                .when(6).then("06")
                                .when(7).then("07")
                                .when(8).then("08")
                                .when(9).then("09")
                                .when(10).then("10")
                                .when(11).then("11")
                                .when(12).then("12")
                                .otherwise("기타").max().as("month"),
                        toMonth.count().as("countMonth")
                ))
                .from(board)
                .where(board.member.id.eq(memberId))
                .groupBy(toMonth)
                .distinct()
                .fetch();
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

