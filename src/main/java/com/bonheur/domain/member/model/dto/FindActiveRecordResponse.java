package com.bonheur.domain.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindActiveRecordResponse {
    private Long activeDay;
    private Long countHappy;
    private Long countTag;
    private Long recordDay;

    public FindActiveRecordResponse updateActiveDayAndRecordDay(Long activeDay, Long recordDay){
        this.activeDay = activeDay;
        this.recordDay = recordDay;
        return this;
    }
}
