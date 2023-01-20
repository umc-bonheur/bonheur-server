package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.QBoard;
import com.bonheur.domain.boardtag.model.QBoardTag;
import com.bonheur.domain.image.model.QImage;
import com.bonheur.domain.tag.model.QTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.bonheur.domain.board.model.QBoard.board;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Board findBoardByIdWithTagAndImage(Long boardId) {
        return queryFactory.select(board)
                .from(board)
                .where(
                        board.id.eq(boardId)
                )
                .leftJoin(QImage.image)
                .fetchJoin()
                .leftJoin(QBoardTag.boardTag)
                .fetchJoin()
                .leftJoin(QTag.tag)
                .fetchJoin()
                .fetchOne();
    }
}
