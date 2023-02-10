package com.bonheur.domain.boardtag.service;

import com.bonheur.domain.board.model.Board;

import java.util.List;

public interface BoardTagService {
    void createBoardTags(Long memberId, Board board, List<Long> tagIds);
    void updateBoardTags(Long memberId, Board board, List<Long> tagIds);
}