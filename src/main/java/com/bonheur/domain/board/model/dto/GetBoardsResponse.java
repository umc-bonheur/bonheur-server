package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsResponse {
    private String contents;
    private List<String> boardTags;
    private String image; // 대표 이미지 url

    private GetBoardsResponse(String contents, List<String> boardTags) {
        this.contents = contents;
        this.boardTags = boardTags;
    }

    public static GetBoardsResponse of(String contents, List<String> boardTags, String image) {
        return new GetBoardsResponse(contents, boardTags, image);
    }

    public static GetBoardsResponse withoutImage(String contents, List<String> boardTags) {
        return new GetBoardsResponse(contents, boardTags);
    }
}