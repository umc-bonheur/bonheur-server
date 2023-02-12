package com.bonheur.domain.board.service;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.board.model.dto.GetBoardsRequest;
import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.common.exception.ForbiddenException;
import com.bonheur.domain.common.exception.InvalidException;
import com.bonheur.domain.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Calendar;

import static com.bonheur.domain.common.exception.dto.ErrorCode.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardServiceHelper {
    public static Board findBoardByMemberId(BoardRepository boardRepository, Long memberId, Long boardId) {
        Board board = boardRepository.findBoardByIdWithMemberAndImages(memberId);
        if (board == null) {
            throw new NotFoundException(String.format("해당하는 게시물(%s)는 존재하지 않습니다.", boardId), E404_NOT_EXISTS_BOARD);
        }
        if (!board.getMember().getId().equals(memberId)) {
            throw new ForbiddenException(String.format("해당 회원(%s)에게 권한이 없습니다.", memberId), E403_FORBIDDEN_BOARD);
        }
        return board;
    }

    public static Board getExistBoard(BoardRepository boardRepository, Long boardId){
        return boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException("존재하지 않은 게시글입니다.", E404_NOT_EXISTS_BOARD));
    }

    public static Board getBoardByMemberId(Long memberId, BoardRepository boardRepository, Long boardId){
        Board board = getExistBoard(boardRepository, boardId);
        if(!board.getMember().getId().equals(memberId)){
            throw new ForbiddenException("해당 회원이 만든 게시글이 아닙니다.", E403_FORBIDDEN_BOARD);
        }
        return board;
    }

    public static void isValidRequest(Long memberId, BoardRepository boardRepository, GetBoardsRequest request) {
        if (boardRepository.getLastIdOfBoard(memberId, "oldest") == null)
            throw new NotFoundException("작성된 게시글이 없습니다.", E404_NOT_EXISTS_WRITTEN_BOARD);

        if (request.getOrderType() == null) request.update("newest");
        if (!(request.getOrderType().equals("newest") || request.getOrderType().equals("oldest")))
            throw new InvalidException("게시글 정렬 순서 입력이 잘못되었습니다.", E400_INVALID_FORMAT_ORDER_TYPE);

        if (request.getLastBoardId() != null) {
            Long lastId = boardRepository.getLastIdOfBoard(memberId, "newest") + (request.getOrderType().equals("newest") ?  1 : 0);
            Long startId = boardRepository.getLastIdOfBoard(memberId, "oldest") - (request.getOrderType().equals("oldest") ? 1 : 0);
            if (!(startId < request.getLastBoardId() && request.getLastBoardId() < lastId))
                throw new InvalidException("더 이상 불러올 글이 없습니다.", E400_INVALID_LAST_BOARD_ID);
        }
    }

    public static LocalDate isValidDateFormat(String localDate) { // 가능한 포맷 : yyyy-MM-dd
        // 1. - 체크(포맷)
        if (localDate.charAt(4) != '-' || localDate.charAt(7) != '-' || localDate.length() != 10)
            throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);
        int idx = 0;
        while (idx < 10) {
            if (idx == 4 || idx == 7) {
                idx++; continue;
            }
            if (localDate.charAt(idx) < '0' || localDate.charAt(idx) > '9') throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);
            System.out.println("idx : "+idx+"\n");
            idx++;
        }

        // 2. 날짜 범위 체크
        int year = Integer.parseInt(localDate.substring(0, 4));
        int month = Integer.parseInt(localDate.substring(5, 7));
        int day = Integer.parseInt(localDate.substring(8, 10));

        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 1);
        int monthLastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (year < 2022 || month < 1 || month > 12 || day < 1 || day > monthLastDay)
            throw new InvalidException("잘못된 날짜 형식이 입력되었습니다", E400_INVALID_FORMAT_DATE);

        // 3. 반환
        return LocalDate.of(year, month, day);
    }
}
