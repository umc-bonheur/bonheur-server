package com.bonheur.domain.board.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBoardResponse {
    private Long boardId;

    public static UpdateBoardResponse of(Long boardId){
        return new UpdateBoardResponse(boardId);
    }
}
