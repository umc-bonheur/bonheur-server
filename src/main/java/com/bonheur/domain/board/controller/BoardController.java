package com.bonheur.domain.board.controller;

import com.bonheur.domain.board.model.GetBoardResponse;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
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

    // # 게시글 전체 조회 (페이징 일단 5개로 정의)
    // 회원 인증 어노테이션 추가 필요
    @GetMapping("/api/boards")
    public ApiResponse<List<GetBoardResponse>> getAllBoards(Long memberId, @PageableDefault(size = 5) Pageable pageable) {
        List<GetBoardResponse> getBoardResponses = boardService.getAllBoards(memberId, pageable);

        return ApiResponse.success(getBoardResponses);
    }
}
