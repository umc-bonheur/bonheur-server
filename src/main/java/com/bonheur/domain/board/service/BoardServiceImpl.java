package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.DeleteBoardResponse;
import com.bonheur.domain.board.model.dto.GetBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.model.BoardTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    // # 게시글 전체 조회
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public List<GetBoardResponse> getAllBoards(Long memberId, Pageable pageable) {
        return boardRepository.findAll(pageable).stream()
                .filter(board -> board.getMember().getId().equals(memberId))
                .map(board -> GetBoardResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl()))
                .collect(Collectors.toList());
    }

    // # Tag Name을 String List로 받아오기
    @Transactional(readOnly = true)
    public List<String> getBoardTagsName(List<BoardTag> boardTags) {
        List<String> tagsName = new ArrayList<>();

        for (BoardTag tag : boardTags) {
            String tagName = tag.getTag().getName();
            tagsName.add(tagName);
        }

        return tagsName;
    }

    // # 게시글 삭제 (boardTag, Image의 cascadeType을 all로 설정해뒀기 때문에 게시글 삭제 시, boardTags와 images 삭제
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional
    public DeleteBoardResponse deleteBoard(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));
        Long writer = board.getMember().getId();
        if (writer == memberId) {
            boardRepository.delete(board);
            return DeleteBoardResponse.builder()
                    .result("success")
                    .build();
        } else {
            return DeleteBoardResponse.builder()
                    .result("fail")
                    .build();
        }
    }

    // # 게시글 조회 - by 해시태그
    // 회원 정보 인증 어노테이션 추가 필요
    @Override
    @Transactional(readOnly = true)
    public List<GetBoardResponse> getBoardsByTag(Long memberId, String tagName, Pageable pageable) {
        return boardRepository.findAll(pageable).stream()
                .filter(board -> board.getMember().getId().equals(memberId))
                .filter(board -> isInTag(board.getBoardTags(), tagName).equals(tagName))
                .map(board -> GetBoardResponse.of(board.getContents(), getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public String isInTag(List<BoardTag> boardTags, String tagName) {
        for (BoardTag tag : boardTags) {
            if (tag.getTag().getName().equals(tagName))
                return tagName;
        }
        return "fail";
    }
}
