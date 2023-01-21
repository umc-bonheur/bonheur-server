package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
     Slice<GetBoardsResponse> getAllBoards(Long lastBoardId, Long memberId, Pageable pageable);
     DeleteBoardResponse deleteBoard(Long memberId, Long boardId);
     Slice<GetBoardsResponse> getBoardsByTag(Long lastBoardId, Long memberId, List<Long> tagIds, Pageable pageable);
     CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException;
     UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException;

     GetBoardResponse getBoard(Long boardId);
}