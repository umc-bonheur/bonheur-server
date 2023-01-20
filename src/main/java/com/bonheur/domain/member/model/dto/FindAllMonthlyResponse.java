package com.bonheur.domain.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllMonthlyResponse {
    private Long countActiveDay;
    private Long countHappy;
    private Long countHashtag;
    private Long countRecordDay;

    public void updateCountActiveDay(Long countActiveDay){
        this.countActiveDay = countActiveDay;
    }

}
