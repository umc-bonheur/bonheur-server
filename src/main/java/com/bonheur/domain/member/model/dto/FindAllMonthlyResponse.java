package com.bonheur.domain.member.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindAllMonthlyResponse {
    private Long dayOfMonth;
    private Long countHappy;
    private Long countHashtag;
    private Long countRecordDay;

    public void updateDayOfMonth(Long dayOfMonth){
        this.dayOfMonth = dayOfMonth;
    }

}
