package com.bonheur.domain.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateBoardResponse {
    private Long boardId;
    @Builder
    private UpdateBoardResponse(Long boardId){
        this.boardId = boardId;
    }

    public static UpdateBoardResponse newResponse(Long boardId){
        return UpdateBoardResponse.builder()
                .boardId(boardId)
                .build();
    }
}
