
package com.bonheur.domain.member.repository;

import com.bonheur.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom {
    Member findMemberById(Long id);
}
