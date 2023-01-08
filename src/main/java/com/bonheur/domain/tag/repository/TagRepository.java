package com.bonheur.domain.tag.repository;

import com.bonheur.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long>,TagRepositoryCustom {
}
