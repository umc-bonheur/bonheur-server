package com.bonheur.domain.board.controller;

import com.bonheur.domain.board.model.GetBoardResponse;
import com.bonheur.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    // 전체 조회 (페이징) : 일단 5개로 정의
    @GetMapping("/boards")
    public List<GetBoardResponse> getAllBoards(@PageableDefault(size = 5) Pageable pageable) {
        return boardService.getAllBoards(pageable);
    }
}
