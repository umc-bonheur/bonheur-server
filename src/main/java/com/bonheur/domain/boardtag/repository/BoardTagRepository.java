package com.bonheur.domain.boardtag.repository;

import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag,Long>,BoardTagRepositoryCustom {
    List<BoardTag> findAllByBoard_Id(Long boardId);
    List<BoardTag> findAllByTag_Id(Long tagId);
}