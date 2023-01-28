package com.bonheur.domain.membertag.repository;

import com.bonheur.domain.membertag.model.MemberTag;
import com.bonheur.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberTagRepository extends JpaRepository<MemberTag,Long>, MemberTagRepositoryCustom {
    Optional<MemberTag> findMemberTagByTag(Tag tag);
}