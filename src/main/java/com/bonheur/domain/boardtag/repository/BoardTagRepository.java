package com.bonheur.domain.boardtag.repository;

import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag,Long>,BoardTagRepositoryCustom {
}
