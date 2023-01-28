package com.bonheur.domain.board.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardResponse {
    private Long boardId;

    public static CreateBoardResponse of(@NotNull Long boardId){
        return new CreateBoardResponse(boardId);
    }
}
