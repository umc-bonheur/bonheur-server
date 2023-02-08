package com.bonheur.domain.board.controller;

import com.bonheur.config.interceptor.Auth;
import com.bonheur.config.resolver.MemberId;
import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.board.model.dto.*;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    // # 게시글 전체 조회 (페이징 일단 5개로 정의)
    @ApiDocumentResponse
    @Operation(summary = "행복기록 전체 조회")
    @GetMapping("/api/boards")
    @Auth
    public ApiResponse<GetBoardsGroupsResponse> getAllBoards(@RequestParam(required = false) Long lastBoardId,
                                                             @Valid @MemberId Long memberId, @RequestParam(defaultValue = "newest") String orderType,
                                                             @PageableDefault(size = 5) Pageable pageable) {
        Slice<GetBoardsResponse> getBoardsResponses = boardService.getAllBoards(lastBoardId, memberId, orderType, pageable);
        GetBoardsGroupsResponse getBoardsGroupsResponse = boardService.getBoardsGroups(getBoardsResponses, orderType);

        return ApiResponse.success(getBoardsGroupsResponse);
    }

    // # 게시글 삭제
    @ApiDocumentResponse
    @Operation(summary = "행복기록 삭제")
    @DeleteMapping("/api/boards/{boardId}")
    @Auth
    public ApiResponse<DeleteBoardResponse> deleteBoard(@Valid @MemberId Long memberId, @PathVariable("boardId") Long boardId) {
        DeleteBoardResponse deleteBoardResponse = boardService.deleteBoard(memberId, boardId);
        return ApiResponse.success(deleteBoardResponse);
    }

    // # 게시글 조회 - 해시태그
    @ApiDocumentResponse
    @Operation(summary = "행복기록 조회 - 해시태그")
    @ResponseBody
    @PostMapping ("/api/boards/tag")
    @Auth
    public ApiResponse<GetBoardsGroupsResponse> getBoardsByTag(
            @RequestParam(required = false) Long lastBoardId,
            @Valid @MemberId Long memberId,
            @Valid @RequestBody GetBoardByTagRequest getBoardByTagRequest, @RequestParam(defaultValue = "newest") String orderType,
            @PageableDefault(size = 5) Pageable pageable) {
        Slice<GetBoardsResponse> getBoardsResponses =
                boardService.getBoardsByTag(lastBoardId, memberId, getBoardByTagRequest.getTagIds(), orderType, pageable);
        GetBoardsGroupsResponse getBoardsGroupsResponse = boardService.getBoardsGroups(getBoardsResponses, orderType);

        return ApiResponse.success(getBoardsGroupsResponse);
    }

    // # 게시글 조회 - by 날짜
    @ApiDocumentResponse
    @Operation(summary = "행복기록 조회 - 날짜별")
    @GetMapping("/api/boards/date")
    @Auth
    public ApiResponse<GetBoardsByDateResponse> getBoardsByDate(@Valid @MemberId Long memberId,
                                                                @RequestParam(required = false) Long lastBoardId,
                                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate,
                                                                @RequestParam(defaultValue = "newest") String orderType,
                                                                Pageable pageable) {
        Slice<GetBoardsResponse> getBoardsResponses = boardService.getBoardsByDate(memberId, lastBoardId,localDate, orderType, pageable);
        Long count = boardService.getNumOfBoardsByDate(memberId, localDate);

        GetBoardsByDateResponse getBoardsByDateResponse = GetBoardsByDateResponse.of(count, getBoardsResponses);
        return ApiResponse.success(getBoardsByDateResponse);
    }

    // # 캘린더 화면
    @ApiDocumentResponse
    @Operation(summary = "행복기록 캘린더 - 작성여부")
    @GetMapping("/api/calendar")
    @Auth
    public ApiResponse<List<GetCalendarResponse>> getCalendar(@Valid @MemberId Long memberId, @RequestParam int year, @RequestParam int month) {
        List<GetCalendarResponse> getCalendarResponseList = boardService.getCalendar(memberId, year, month);
        return ApiResponse.success(getCalendarResponseList);
    }

    @ApiDocumentResponse
    @Operation(summary = "게시물 생성")
    @PostMapping("/api/boards")
    @Auth
    public ApiResponse<CreateBoardResponse> createBoard(
            @Valid @MemberId Long memberId,
            @RequestPart(value = "images") List<MultipartFile> images,
            @RequestPart @Valid CreateBoardRequest createBoardRequest) throws IOException {

        return ApiResponse.success(boardService.createBoard(memberId, createBoardRequest, images));
    }

    @ApiDocumentResponse
    @Operation(summary = "게시물 수정")
    @PatchMapping("/api/boards/{boardId}")
    public ApiResponse<UpdateBoardResponse> updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestPart(value = "images") List<MultipartFile> images,
            @RequestPart @Valid UpdateBoardRequest updateBoardRequest) throws IOException {

        return ApiResponse.success(boardService.updateBoard(boardId, updateBoardRequest, images));
    }

    @ApiDocumentResponse
    @Operation(summary = "게시물 상세 조회")
    // 이상 Swagger 코드
    @GetMapping("/api/boards/{boardId}")
    @Auth
    public ApiResponse<GetBoardResponse> getBoard(
            @PathVariable Long boardId,
            @Valid @MemberId Long memberId
    ) {
        return ApiResponse.success(boardService.getBoard(memberId, boardId));
    }
}