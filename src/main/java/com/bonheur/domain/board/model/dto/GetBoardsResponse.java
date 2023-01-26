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
    private String createdAt;

    private GetBoardsResponse(String contents, List<String> boardTags, String createdAt) {
        this.contents = contents;
        this.boardTags = boardTags;
        this.createdAt = createdAt;
    }

    public static GetBoardsResponse of(String contents, List<String> boardTags, String image, String createdAt) {
        if (image == null) return new GetBoardsResponse(contents, boardTags, createdAt);
        return new GetBoardsResponse(contents, boardTags, image, createdAt);
    }
}