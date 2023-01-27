package com.bonheur.domain.board.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBoardResponse {
    private Long boardId;

    public static UpdateBoardResponse of(@NotNull Long boardId){
        return new UpdateBoardResponse(boardId);
    }
}
