package com.bonheur.domain.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCalendarResponse {
    private int day; // 일
    private Long count; // 작성한 개수

    public static GetCalendarResponse of(int day, Long count) {
        return new GetCalendarResponse(day, count);
    }
}
