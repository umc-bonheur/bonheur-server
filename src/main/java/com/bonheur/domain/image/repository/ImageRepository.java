package com.bonheur.domain.image.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long>,ImageRepositoryCustom {
    List<Image> findAllByBoard(Board board);
}
