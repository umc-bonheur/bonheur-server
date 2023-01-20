package com.bonheur.domain.board.controller;

import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.board.model.dto.*;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ApiResponse<CreateBoardResponse> createBoard(
            @RequestPart(value = "images") List<MultipartFile> images,
            @RequestPart CreateBoardRequest createBoardRequest) throws IOException {

        Long memberId = 1L; //session 관련 검증 추가해야 함!

        return ApiResponse.success(boardService.createBoard(memberId, createBoardRequest, images));
    }

    @PatchMapping("/api/boards/{boardId}")
    public ApiResponse<UpdateBoardResponse> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestPart(value = "images") List<MultipartFile> images,
            @RequestPart UpdateBoardRequest updateBoardRequest) throws IOException {

        return ApiResponse.success(boardService.updateBoard(boardId, updateBoardRequest, images));
    }

    @ApiDocumentResponse
    @Operation(summary = "게시물 상세 조회")
    // 이상 Swagger 코드
    @GetMapping("/api/boards/{boardId}")
    public ApiResponse<GetBoardResponse> getBoard(
            @PathVariable Long boardId,
            Long memberId
    ) {
        GetBoardResponse response = boardService.getBoard(boardId);
        return ApiResponse.success(response);
    }
}
