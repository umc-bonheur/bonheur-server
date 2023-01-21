package com.bonheur.domain.tag.service;

import com.bonheur.domain.board.model.Board;

import java.util.List;

public interface TagService {
    void createBoardTags(Board board, List<String> tags);
    void updateBoardTags(Board board, List<String> tags);
}
