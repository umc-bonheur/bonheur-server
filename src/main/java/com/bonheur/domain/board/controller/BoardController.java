package com.bonheur.domain.board.controller;

import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ApiResponse<CreateBoardResponse> createBoard(
            @RequestPart(value = "images", required=false) List<MultipartFile> images,
            @RequestPart CreateBoardRequest request) throws IOException {

        return ApiResponse.success(boardService.createBoard(request, images));
    }
}
