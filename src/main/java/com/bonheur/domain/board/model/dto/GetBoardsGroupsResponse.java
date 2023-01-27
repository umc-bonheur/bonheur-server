package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsGroupsResponse {
    private Map<String, List<GetBoardsResponse>> group;
    public static GetBoardsGroupsResponse of(Map<String, List<GetBoardsResponse>> group) {
        return new GetBoardsGroupsResponse(group);
    }
}
