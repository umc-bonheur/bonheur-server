package com.bonheur.domain.board.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardResponse {
    private Long boardId;

    public static CreateBoardResponse of(Long boardId){
        return new CreateBoardResponse(boardId);
    }
}
