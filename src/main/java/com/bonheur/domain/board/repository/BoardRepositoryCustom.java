package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardRepositoryCustom {
    Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, Pageable pageable);
    Slice<Board> findByTagWithPaging(Long lastBoardId, Long memberId, Long tagId, Pageable pageable);
    Board findBoardByIdWithTagAndImage(Long boardId);
}
