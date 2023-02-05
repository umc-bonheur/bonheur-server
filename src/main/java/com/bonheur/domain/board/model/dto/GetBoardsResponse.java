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
    private Long boardId;
    private String contents;
    private List<String> boardTags;
    private String image; // 대표 이미지 url
    private String createdAtDate; // 형식 : 00월 00일 0요일
    private String createdAtTime; // 형식 : 0M 00:00

    public static GetBoardsResponse of(Long boardId, String contents, List<String> boardTags, String image, String createdAtDate, String createdAtTime) {
        return new GetBoardsResponse(boardId, contents, boardTags, image, createdAtDate, createdAtTime);
    }
}