package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteBoardResponse {
    private Long boardId;

    // parameter 한 개여서 public으로 작성
    @Builder
    public DeleteBoardResponse(Long boardId){
        this.boardId = boardId;
    }
}