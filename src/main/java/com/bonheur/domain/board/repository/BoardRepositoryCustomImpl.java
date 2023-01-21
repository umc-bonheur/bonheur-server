package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.image.model.QImage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.bonheur.domain.board.model.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    // # 게시글 전체 조회 (무한 스크롤, no-offset)
    public Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, Pageable pageable) {
        List<Board> results = queryFactory.selectFrom(board)
                .where(
                        // no-offset 페이징 처리
                        ltBoardId(lastBoardId),
                        // memberId
                        board.member.id.eq(memberId)
                ).orderBy(board.createdAt.desc())
                .limit(pageable.getPageSize() + 1) // Slice 방식
                .fetch();

        // 무한 스크롤 처리
        return checkLastPage(pageable, results);
    }

    // no-offset 방식 처리 (처음 조회시 boardId가 null로 들어옴)
    private BooleanExpression ltBoardId(Long boardId) {
        if (boardId == null) return null;
        return board.id.lt(boardId);
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
    public Board findBoardByIdWithTagAndImage(Long boardId) {
        return queryFactory.select(board)
                .from(board)
                .leftJoin(board.images,QImage.image)
                .fetchJoin()
                .where(
                        board.id.eq(boardId)
                )
                .fetchOne();
    }
}
