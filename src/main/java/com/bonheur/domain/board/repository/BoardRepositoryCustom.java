package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;

public interface BoardRepositoryCustom {
    Board findBoardByIdWithTagAndImage(Long boardId);
}
