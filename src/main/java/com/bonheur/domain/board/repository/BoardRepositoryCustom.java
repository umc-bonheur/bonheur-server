package com.bonheur.domain.board.repository;

import com.bonheur.domain.board.model.dto.GetBoardResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface BoardRepositoryCustom {
    public Slice<GetBoardResponse> findAllWithPaging(Long memberId, Pageable pageable);
}
