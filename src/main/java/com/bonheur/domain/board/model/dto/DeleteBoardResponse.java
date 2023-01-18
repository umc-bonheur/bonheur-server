package com.bonheur.domain.board.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteBoardResponse {
    private String result;

    // parameter 한 개여서 public으로 작성
    @Builder
    public DeleteBoardResponse(String result){
        this.result = result;
    }
}
