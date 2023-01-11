package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.GetBoardResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface BoardService {
    public List<GetBoardResponse> getAllBoards(Pageable pageable);
}
