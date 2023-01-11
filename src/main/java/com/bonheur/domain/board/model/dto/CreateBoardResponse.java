package com.bonheur.domain.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBoardResponse {
    private Long boardId;

    @Builder
    public CreateBoardResponse(Long boardId){
        this.boardId = boardId;
    }
}
