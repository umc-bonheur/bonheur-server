package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.FindAllActiveResponse;
import com.bonheur.domain.member.model.dto.FindByTagResponse;
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

        // 2022-01-20 03:48:02.164938 -> 2022-01-20 형식으로 변환
        StringTemplate toDate = stringTemplate("date({0})", board.createdAt);

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
    public Long findByDay(Long memberId, String day){
        // todo : 사용할 데이터베이스 문법으로 변경

        /* mysql */
         StringTemplate toDay = stringTemplate("DAYOFWEEK({0})", board.createdAt);

        /* h2 */
        // StringTemplate toDay = stringTemplate("DAY_OF_WEEK({0})", board.createdAt);

        Long countDayOfWeek = queryFactory
                .select(toDay.count())
                .from(board)
                .where(board.member.id.eq(memberId),
                        toDay.eq(day))
                .groupBy(toDay)
                .distinct()
                .fetchFirst();

        return isNull(countDayOfWeek) ? 0 :countDayOfWeek;
    }

    @Override
    public Long findByMonth(Long memberId, String month) {
        // todo : 사용할 데이터베이스 문법으로 변경

        /* mysql */
        StringTemplate toMonth = stringTemplate("DATE_FORMAT({0}, '%m')", board.createdAt);

        /* h2 */
        // StringTemplate toMonth = stringTemplate("FORMATDATETIME({0}, 'MM')", board.createdAt);

        Long countByMonth = queryFactory
                .select(toMonth.count())
                .from(board)
                .where(board.member.id.eq(memberId),
                        toMonth.eq(month))
                .groupBy(toMonth)
                .distinct()
                .fetchFirst();

        return isNull(countByMonth) ? 0 :countByMonth;
    }

}

