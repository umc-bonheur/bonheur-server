package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepositoryCustom {
    Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, Pageable pageable);
    Slice<Board> findByTagWithPaging(Long lastBoardId, Long memberId, List<Long> tagIds, Pageable pageable);
    Board findBoardByIdWithTagAndImage(Long boardId);
    Slice<Board> findByDateWithPaging(Long lastBoardId, Long memberId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
