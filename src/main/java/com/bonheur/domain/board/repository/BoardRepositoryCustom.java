package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardRepositoryCustom {
    public Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, Pageable pageable);
}
