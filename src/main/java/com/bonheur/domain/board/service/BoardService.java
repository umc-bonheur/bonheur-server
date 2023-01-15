package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
     CreateBoardResponse createBoard(CreateBoardRequest request, List<MultipartFile> images) throws IOException;
}