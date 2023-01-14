package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.GetBoardResponse;
import com.bonheur.domain.boardtag.model.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface BoardService {
    public List<GetBoardResponse> getAllBoards(Long memberId, Pageable pageable);
    public List<String> getBoardTagsName(List<BoardTag> boardTags);
    public String deleteBoard(Long memberId, Long boardId);
}
