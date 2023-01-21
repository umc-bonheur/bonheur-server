package com.bonheur.domain.tag.repository;

import com.bonheur.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long>,TagRepositoryCustom {
    Optional<Tag> findTagByName(String name); //태그 이름으로 해당 tag 찾기
}
