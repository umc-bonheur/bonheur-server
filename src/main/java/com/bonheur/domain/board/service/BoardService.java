package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
     CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException;
     UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException;

     GetBoardResponse getBoard(Long boardId);
}