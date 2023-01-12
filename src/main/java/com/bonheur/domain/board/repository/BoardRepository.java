package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Board findBoardById(Long id);
}
