package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.GetCalendarResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BoardRepositoryCustom {
    Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, Pageable pageable);
    Slice<Board> findByTagWithPaging(Long lastBoardId, Long memberId, List<Long> tagIds, Pageable pageable);
    Board findBoardByIdWithTagAndImage(Long boardId);
    List<GetCalendarResponse> getCalendar(Long memberId, int year, int month, int lastDay);
}
