package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.bonheur.domain.board.model.QBoard.board;
import static com.bonheur.domain.boardtag.model.QBoardTag.boardTag;
import static com.bonheur.domain.member.model.QMember.member;
import static com.bonheur.domain.tag.model.QTag.tag;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
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
    public FindAllActiveResponse findAllActive(Long memberId) {

        /* mysql */
        StringTemplate toDate = stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", board.createdAt);

        /* h2 */
        // StringTemplate toDate = stringTemplate("FORMATDATETIME({0}, 'yyyy-MM-dd')", board.createdAt);

        return queryFactory
                .select(Projections.fields(FindAllActiveResponse.class,
                        board.countDistinct().as("countHappy"),
                        boardTag.tag.name.countDistinct().as("countHashtag"),
                        ExpressionUtils.as(
                                JPAExpressions.select(toDate.countDistinct())
                                .from(board)
                                .where(board.member.id.eq(memberId))
                                .groupBy(toDate)
                                ,"countRecordDay")
                        ))
                .from(boardTag, board)
                .where(boardTag.board.member.id.eq(memberId),
                        board.member.id.eq(memberId))
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
    public Long findByTime(Long memberId, String start, String end) {
        // todo : 사용할 데이터베이스 문법으로 변경

        /* mysql */
        StringTemplate toTime = stringTemplate("DATE_FORMAT({0}, '%h')", board.createdAt);

        /* h2 */
        // StringTemplate toTime = stringTemplate("FORMATDATETIME({0}, 'HH')", board.createdAt);

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
        // todo : 사용할 데이터베이스 문법으로 변경

        /* mysql */
        StringTemplate toTime = stringTemplate("DATE_FORMAT({0}, '%h')", board.createdAt);

        /* h2 */
        // StringTemplate toTime = stringTemplate("FORMATDATETIME({0}, 'HH')", board.createdAt);

        Long result = queryFactory
                .select(toTime.count())
                .from(board)
                .where(board.member.id.eq(memberId),
                        toTime.between("20", "25").or(toTime.between("00","01")))
                .groupBy(toTime)
                .distinct()
                .fetchFirst();

        return isNull(result) ? 0 : result;
    }

    @Override
    public List<FindByDayResponse> findByDay(Long memberId){
        // todo : 사용할 데이터베이스 문법으로 변경

        /* mysql */
        StringTemplate toDay = stringTemplate("DAYOFWEEK({0})", board.createdAt);

        /* h2 */
        // StringTemplate toDay = stringTemplate("DAY_OF_WEEK({0})", board.createdAt);

        return queryFactory
                .select(Projections.fields(FindByDayResponse.class,
                                        toDay.when("1").then("sun")
                                                .when("2").then("mon")
                                                .when("3").then("tue")
                                                .when("4").then("wed")
                                                .when("5").then("thr")
                                                .when("6").then("fri")
                                                .when("7").then("sat")
                                                .otherwise("기타").max().as("dayOfWeek"),
                                        toDay.count().as("countDay")
                        ))
                .from(board)
                .where(board.member.id.eq(memberId))
                .groupBy(toDay)
                .orderBy(toDay.asc())
                .distinct()
                .fetch();
    }

    @Override
    public List<FindByMonthResponse> findByMonth(Long memberId) {
        // todo : 사용할 데이터베이스 문법으로 변경

        /* mysql */
        // StringTemplate toMonth = stringTemplate("DATE_FORMAT({0}, '%m')", board.createdAt);

        /* h2 */
        StringTemplate toMonth = stringTemplate("FORMATDATETIME({0}, 'MM')", board.createdAt);

        return queryFactory
                .select(Projections.fields(FindByMonthResponse.class,
                        toMonth.when("01").then("jan")
                                .when("02").then("feb")
                                .when("03").then("mar")
                                .when("04").then("apr")
                                .when("05").then("may")
                                .when("06").then("jun")
                                .when("07").then("jul")
                                .when("08").then("aug")
                                .when("09").then("sept")
                                .when("10").then("oct")
                                .when("11").then("nov")
                                .when("12").then("dec")
                                .otherwise("기타").max().as("month"),
                        toMonth.count().as("countMonth")
                ))
                .from(board)
                .where(board.member.id.eq(memberId))
                .groupBy(toMonth)
                .orderBy(toMonth.asc())
                .distinct()
                .fetch();
    }

}

