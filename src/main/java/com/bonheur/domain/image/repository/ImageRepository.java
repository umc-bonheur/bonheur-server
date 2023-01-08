package com.bonheur.domain.image.repository;

import com.bonheur.domain.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long>,ImageRepositoryCustom {
}
