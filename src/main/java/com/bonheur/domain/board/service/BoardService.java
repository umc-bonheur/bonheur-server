package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.CreateBoardRequest;

public interface BoardService {
     Long createBoard(CreateBoardRequest createBoardReq);
}
