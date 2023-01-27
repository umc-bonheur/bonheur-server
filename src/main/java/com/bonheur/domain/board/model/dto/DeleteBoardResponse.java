package com.bonheur.domain.board.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteBoardResponse {
    private Long boardId;

    public static DeleteBoardResponse of(Long boardId) {
        return new DeleteBoardResponse(boardId);
    }
}