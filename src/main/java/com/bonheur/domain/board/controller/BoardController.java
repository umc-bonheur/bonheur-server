package com.bonheur.domain.board.controller;

import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;
    private final TagService tagService;

    @PostMapping("/api/board/create")
    public ApiResponse<CreateBoardResponse> createBoard(
            @RequestPart(value = "images", required=false) List<MultipartFile> images,
            @RequestPart(value = "tags", required = false) List<String> tags,
            @RequestPart(value = "CreateBoardRequest") CreateBoardRequest createBoardReq){

        Long boardId = boardService.createBoard(createBoardReq);
        List<Tag> tagList = tagService.createTag(tags);

        CreateBoardResponse createBoardResponse = CreateBoardResponse.builder()
                .boardId(boardId)
                .build();

        return ApiResponse.success(createBoardResponse);
    }
}
