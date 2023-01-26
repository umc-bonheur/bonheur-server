package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsGroupsResponse {
    private String date; // 형식 : 00월 00일 0요일
    private List<GetBoardsResponse> getBoardsResponses;

    public static GetBoardsGroupsResponse of(String date, List<GetBoardsResponse> getBoardsResponses) {
        return new GetBoardsGroupsResponse(date, getBoardsResponses);
    }
}
