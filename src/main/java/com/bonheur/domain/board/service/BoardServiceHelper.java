package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.common.exception.ForbiddenException;
import com.bonheur.domain.common.exception.NotFoundException;
import com.bonheur.domain.common.exception.dto.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardServiceHelper {
    public static Board findBoardByMemberId(BoardRepository boardRepository, Long memberId, Long boardId) {
        Board board = boardRepository.findBoardByIdWithMemberAndImages(memberId);
        if (board == null) {
            throw new NotFoundException(String.format("해당하는 게시물(%s)는 존재하지 않습니다.", boardId), ErrorCode.E404_NOT_EXISTS);
        }
        if (!board.getMember().getId().equals(memberId)) {
            throw new ForbiddenException(String.format("해당 회원(%s)에게 권한이 없습니다.", memberId));
        }
        return board;
    }
}
