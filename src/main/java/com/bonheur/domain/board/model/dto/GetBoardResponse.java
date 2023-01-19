package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardResponse {
    private String contents;
    private List<String> boardTags;
    private String image; // 대표 이미지 url

    public static GetBoardResponse of(String contents, List<String> boardTags, String image) {
        return new GetBoardResponse(contents, boardTags, image);
    }
    public static GetBoardResponse withoutImage(String contents, List<String> boardTags) {
        return new GetBoardResponse(contents, boardTags, null);
    }
}