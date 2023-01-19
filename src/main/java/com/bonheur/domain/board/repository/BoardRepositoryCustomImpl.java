package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Board> findAllWithPaging(Long memberId, Pageable pageable) {
        QBoard board = QBoard.board;

        return queryFactory.selectFrom(board)
                .where(board.member.id.eq(memberId))
                .orderBy(board.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
    }
}
