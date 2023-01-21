package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.dto.DeleteBoardResponse;
import com.bonheur.domain.board.model.dto.GetBoardResponse;
import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import com.bonheur.domain.board.model.dto.CreateBoardRequest;
import com.bonheur.domain.board.model.dto.CreateBoardResponse;
import com.bonheur.domain.board.model.dto.UpdateBoardRequest;
import com.bonheur.domain.board.model.dto.UpdateBoardResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface BoardService {
    public Slice<GetBoardResponse> getAllBoards(Long lastBoardId, Long memberId, Pageable pageable);

    public List<String> getBoardTagsName(List<BoardTag> boardTags);

    public DeleteBoardResponse deleteBoard(Long memberId, Long boardId);

    public Slice<GetBoardResponse> getBoardsByTag(Long lastBoardId, Long memberId, String tagName, Pageable pageable);
    CreateBoardResponse createBoard(Long memberId, CreateBoardRequest request, List<MultipartFile> images) throws IOException;
    UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest request, List<MultipartFile> images) throws IOException;
}