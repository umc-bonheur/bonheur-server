package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    List<Board> findByMember(Member member);
}
