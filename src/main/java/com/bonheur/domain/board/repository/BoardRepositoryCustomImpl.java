package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.QBoard;
import com.bonheur.domain.board.model.dto.GetBoardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    // # 게시글 조회 (무한 스크롤, no-offset) 2 - 도전 예정


    // # 게시글 조회 (무한 스크롤) 1 - 근데 이거 실패함 ㅠnㅠ
    public Slice<GetBoardResponse> findAllWithPaging(Long memberId, Pageable pageable) {
        QBoard board = QBoard.board;

        List<GetBoardResponse> getBoardResponseList = queryFactory
                .select(Projections.fields(GetBoardResponse.class,
                        board.contents, board.boardTags, board.images)).from(board)
                .where(board.member.id.eq(memberId))
                .orderBy(board.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1).fetch();

        List<GetBoardResponse> content = new ArrayList<>();
        for (GetBoardResponse res : getBoardResponseList) {
            content.add(GetBoardResponse.of(res.getContents(), res.getBoardTags(), res.getImage()));
        }

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
