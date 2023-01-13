package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.GetBoardResponse;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public List<GetBoardResponse> getAllBoards(Long memberId, Pageable pageable) {

        // to do : memberId에 따라
        return boardRepository.findAll(pageable).stream()
                .filter(board -> board.getMember().getId().equals(memberId))
                .map(board -> GetBoardResponse.of(board, getBoardTagsName(board.getBoardTags()), board.getImages().get(0).getUrl()))
                .collect(Collectors.toList());
    }

    // # Tag Name을 String List로 받아오기
    public List<String> getBoardTagsName(List<BoardTag> boardTags) {
        List<String> tagsName = new ArrayList<>();

        for (BoardTag tag : boardTags) {
            String tagName = tag.getTag().getName();
            tagsName.add(tagName);
        }

        return tagsName;
    }
}
