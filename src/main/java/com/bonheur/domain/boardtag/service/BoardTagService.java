package com.bonheur.domain.boardtag.service;

import com.bonheur.domain.board.model.Board;

import java.util.List;

public interface BoardTagService {
    void createBoardTags(Board board, List<Long> tagIds);
    void updateBoardTags(Board board, List<Long> tagsIds);
}