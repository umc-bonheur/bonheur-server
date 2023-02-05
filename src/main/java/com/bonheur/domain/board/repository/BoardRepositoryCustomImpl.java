package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberOperation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.bonheur.domain.board.model.QBoard.board;
import static com.bonheur.domain.boardtag.model.QBoardTag.boardTag;
import static com.bonheur.domain.image.model.QImage.image;
import static com.bonheur.domain.member.model.QMember.member;
import static com.bonheur.domain.tag.model.QTag.tag;
import static com.querydsl.core.types.dsl.Expressions.numberOperation;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    // # 게시글 전체 조회 (무한 스크롤, no-offset)
    public Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, String orderType, Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = orderType.equals("newest") ? board.createdAt.desc() : board.createdAt.asc();
        List<Board> results = queryFactory.selectFrom(board)
                .where(
                        // no-offset 페이징 처리
                        ltBoardId(lastBoardId, orderType),
                        // memberId
                        board.member.id.eq(memberId)
                ).orderBy(orderSpecifier)
                .limit(pageable.getPageSize() + 1) // Slice 방식
                .fetch();

        // 무한 스크롤 처리
        return checkLastPage(pageable, results);
    }

    @Override
    // # 게시글 조회 - by Tag (무한 스크롤, no-offset)
    public Slice<Board> findByTagWithPaging(Long lastBoardId, Long memberId, List<Long> tagIds, String orderType, Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = orderType.equals("newest") ? board.createdAt.desc() : board.createdAt.asc();
        List<Board> results = queryFactory.selectFrom(board)
                .where(
                        // no-offset 페이징 처리
                        ltBoardId(lastBoardId, orderType),
                        // memberId
                        board.member.id.eq(memberId),
                        // tag
                        board.id.in(queryFactory.select(boardTag.board.id).from(boardTag)
                                .where(tag.id.in(tagIds))
                                .leftJoin(tag).on(tag.id.eq(boardTag.tag.id))
                        )
                )
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize() + 1) // Slice 방식
                .fetch();

        // 무한 스크롤 처리
        return checkLastPage(pageable, results);
    }

    // # 게시글 조회 - 날짜별
    @Override
    public Slice<Board> findByCreatedAtWithPaging(Long lastBoardId, Long memberId, LocalDate localDate, String orderType, Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = orderType.equals("newest") ? board.createdAt.desc() : board.createdAt.asc();
        NumberOperation<Integer> toYear = numberOperation(Integer.class, Ops.DateTimeOps.YEAR, board.createdAt);
        NumberOperation<Integer> toMonth = numberOperation(Integer.class, Ops.DateTimeOps.MONTH, board.createdAt);
        NumberOperation<Integer> toDay = numberOperation(Integer.class, Ops.DateTimeOps.DAY_OF_MONTH, board.createdAt);
        List<Board> results = queryFactory.selectFrom(board)
                .where(
                        // no-offset 페이징 처리
                        ltBoardId(lastBoardId, orderType),
                        // memberId
                        board.member.id.eq(memberId),
                        // createdAt
                        toYear.eq(localDate.getYear()),
                        toMonth.eq(localDate.getMonthValue()),
                        toDay.eq(localDate.getDayOfMonth())
                )
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize() + 1) // Slice 방식
                .fetch();

        // 무한 스크롤 처리
        return checkLastPage(pageable, results);
    }

    // no-offset 방식 처리 (처음 조회시 boardId가 null로 들어옴)
    private BooleanExpression ltBoardId(Long boardId, String orderType) {
        if (boardId == null) return null;
        if (orderType.equals("newest"))
            return board.id.lt(boardId);
        else return board.id.gt(boardId);
    }

    // 무한 스크롤 방식 처리
    private Slice<Board> checkLastPage(Pageable pageable, List<Board> results) {
        boolean hasNext = false; // pagesize보다 1 크게 가져와서 다음 페이지가 남았는지 확인

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize()); // 필요없으므로 삭제
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    @Override
    // # 캘린더 조회
    public List<Integer> getCalendar(Long memberId, int year, int month, int lastDay) {
        NumberOperation<Integer> toDay = numberOperation(Integer.class, Ops.DateTimeOps.DAY_OF_MONTH, board.createdAt);
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, month, lastDay, 23, 59, 59);

        return queryFactory.select(toDay).from(board)
                .where(
                        // memberId
                        board.member.id.eq(memberId),
                        // 월마다 날짜별 count
                        board.createdAt.between(start, end)
                )
                .groupBy(toDay)
                .fetch();
    }

    // # 게시글 날짜별 조회 count
    @Override
    public Long getNumOfBoardsByDate(Long memberId, LocalDate localDate) {
        NumberOperation<Integer> toYear = numberOperation(Integer.class, Ops.DateTimeOps.YEAR, board.createdAt);
        NumberOperation<Integer> toMonth = numberOperation(Integer.class, Ops.DateTimeOps.MONTH, board.createdAt);
        NumberOperation<Integer> toDay = numberOperation(Integer.class, Ops.DateTimeOps.DAY_OF_MONTH, board.createdAt);

        return queryFactory.select(board.count())
                .from(board)
                .where(
                        // memberId
                        board.member.id.eq(memberId),
                        // createdAt
                        toYear.eq(localDate.getYear()),
                        toMonth.eq(localDate.getMonthValue()),
                        toDay.eq(localDate.getDayOfMonth())
                ).fetchOne();
    }

    @Override
    public Board findBoardByIdWithMemberAndImages(Long boardId) {
        return queryFactory.select(board)
                .from(board)
                .leftJoin(board.images, image)
                .fetchJoin()
                .leftJoin(board.member, member)
                .fetchJoin()
                .where(
                        board.id.eq(boardId)
                )
                .fetchOne();
    }
}
