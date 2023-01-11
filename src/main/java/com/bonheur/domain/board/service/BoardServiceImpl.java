package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.GetBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    public List<GetBoardResponse> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable).stream()
                .map(GetBoardResponse::new)
                .collect(Collectors.toList());
    }
}
