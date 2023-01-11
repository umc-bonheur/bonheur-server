package com.bonheur.domain.board.controller;

import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.service.BoardService;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.image.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/board/create")
    public Long createBoard(
            @RequestPart(value = "images", required=false) List<Image> images,
            @RequestPart(value = "tags", required = false) List<BoardTag> tags,
            @RequestPart(value = "createBoardReq") CreateBoardRequest createBoardReq){
        return boardService.create(createBoardReq, images, tags);
    }
}
