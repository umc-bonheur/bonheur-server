package com.bonheur.domain.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardResponse {
    private String contents;
    private List<String> boardTags;
    private String image; // 대표 이미지 url

    public static GetBoardResponse of(Board board, List<String> boardTags, String image) {
        return new GetBoardResponse(board.getContents(), boardTags, image);
    }
}