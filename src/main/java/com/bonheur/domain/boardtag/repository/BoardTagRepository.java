package com.bonheur.domain.boardtag.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag,Long>,BoardTagRepositoryCustom {
    List<BoardTag> findAllByBoard(Board board);
    List<BoardTag> findAllByTag(Tag tag);
}