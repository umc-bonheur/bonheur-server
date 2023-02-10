package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.common.exception.NotFoundException;
import com.bonheur.domain.common.exception.UnAuthorizedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.bonheur.domain.common.exception.dto.ErrorCode.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardServiceHelper {

    public static Board getExistBoard(BoardRepository boardRepository, Long boardId){
        return boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException("존재하지 않은 게시글입니다.", E404_NOT_EXISTS_BOARD));
    }

    public static Board getBoardByMemberId(Long memberId, BoardRepository boardRepository, Long boardId){
        Board board = getExistBoard(boardRepository, boardId);
        if(!board.getMember().getId().equals(memberId)){
            throw new UnAuthorizedException("해당 회원이 만든 게시글이 아닙니다.", E401_UNAUTHORIZED_BOARD);
        }
        return board;
    }
}
