package com.bonheur.domain.board.controller;

import com.bonheur.domain.board.model.dto.DeleteBoardResponse;
import com.bonheur.domain.board.model.dto.GetBoardResponse;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.common.exception.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // # 게시글 삭제
    // 회원 인증 어노테이션 추가 필요
    @DeleteMapping("/api/boards/{boardId}")
    public ApiResponse<DeleteBoardResponse> deleteBoard(Long memberId, @PathVariable("boardId") Long boardId) {
        DeleteBoardResponse deleteBoardResponse = boardService.deleteBoard(memberId, boardId);
        if (deleteBoardResponse.getResult().equals("fail"))
            return ApiResponse.error(ErrorCode.E400_INVALID_AUTH_TOKEN);
        else return ApiResponse.success(deleteBoardResponse);
    }
}
