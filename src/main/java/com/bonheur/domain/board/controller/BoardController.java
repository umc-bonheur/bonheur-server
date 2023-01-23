package com.bonheur.domain.board.controller;

import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.board.model.dto.*;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.common.exception.dto.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    // # 게시글 전체 조회 (페이징 일단 5개로 정의)
    // 회원 인증 어노테이션 추가 필요
    @GetMapping("/api/boards")
    public ApiResponse<Slice<GetBoardsResponse>> getAllBoards(Long lastBoardId, Long memberId, @PageableDefault(size = 5) Pageable pageable) {
        Slice<GetBoardsResponse> getBoardsResponses = boardService.getAllBoards(lastBoardId, memberId, pageable);

        return ApiResponse.success(getBoardsResponses);
    }

    // # 게시글 삭제
    // 회원 인증 어노테이션 추가 필요
    @DeleteMapping("/api/boards/{boardId}")
    public ApiResponse<DeleteBoardResponse> deleteBoard(Long memberId, @PathVariable("boardId") Long boardId) {
        DeleteBoardResponse deleteBoardResponse = boardService.deleteBoard(memberId, boardId);
        if (deleteBoardResponse.getBoardId().equals(boardId))
            return ApiResponse.success(deleteBoardResponse);
        else return ApiResponse.error(ErrorCode.E400_INVALID_AUTH_TOKEN);
    }

    // # 게시글 조회 - 해시태그
    // 회원 인증 어노테이션 추가 필요
    @ResponseBody
    @PostMapping ("/api/boards/tag")
    public ApiResponse<Slice<GetBoardsResponse>> getBoardsByTag(
            @RequestParam(required = false) Long lastBoardId, @RequestBody GetBoardByTagRequest getBoardByTagRequest,
            @RequestParam(required = false) @PageableDefault(size = 5) Pageable pageable) {
        Slice<GetBoardsResponse> getBoardsResponses =
                boardService.getBoardsByTag(lastBoardId, getBoardByTagRequest.getMemberId(), getBoardByTagRequest.getTagIds(), pageable);

        return ApiResponse.success(getBoardsResponses);
    }

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
        return ApiResponse.success(boardService.getBoard(boardId));
    }
}