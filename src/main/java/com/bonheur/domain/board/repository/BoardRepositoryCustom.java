package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.List;

public interface BoardRepositoryCustom {
    Slice<Board> findAllWithPaging(Long lastBoardId, Long memberId, String orderType, Pageable pageable);
    Slice<Board> findByTagWithPaging(Long lastBoardId, Long memberId, List<Long> tagIds, String orderType, Pageable pageable);
    Board findBoardByIdWithMemberAndImages(Long boardId);
    List<Integer> getCalendar(Long memberId, int year, int month, int lastDay);
    Slice<Board> findByCreatedAtWithPaging(Long lastBoardId, Long memberId, LocalDate localDate, String orderType, Pageable pageable);
    Long getNumOfBoardsByDate(Long memberId, LocalDate localDate);
    Long getLastIdOfBoard(Long memberId, String orderType);
}
