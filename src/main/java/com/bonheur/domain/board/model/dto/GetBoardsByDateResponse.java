package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsByDateResponse {
    private Long count; // 해당 날짜에 몇 개의 기록을 했는지 -> 화면상 : 오늘 count개의 행복을 찾았어요
    private Slice<GetBoardsResponse> getBoardsResponses;

    public static GetBoardsByDateResponse of(Long count, Slice<GetBoardsResponse> getBoardsResponses) {
        return new GetBoardsByDateResponse(count, getBoardsResponses);
    }
}
