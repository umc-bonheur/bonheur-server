package com.bonheur.domain.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCalendarResponse {
    private int day; // 일
    private Boolean isWrite; // 작성 여부

    public static GetCalendarResponse of(int day, Boolean isWrite) {
        return new GetCalendarResponse(day, isWrite);
    }
}
