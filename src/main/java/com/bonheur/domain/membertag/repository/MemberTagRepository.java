package com.bonheur.domain.membertag.repository;

import com.bonheur.domain.membertag.model.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberTagRepository extends JpaRepository<MemberTag,Long>, MemberTagRepositoryCustom {
}