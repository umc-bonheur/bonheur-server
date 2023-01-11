package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.image.model.Image;

import java.util.List;

public interface BoardService {
     CreateBoardResponse create(CreateBoardRequest createBoardReq, List<Image> images, List<BoardTag> tags);
}
