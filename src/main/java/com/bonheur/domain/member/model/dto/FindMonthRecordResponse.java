package com.bonheur.domain.member.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindMonthRecordResponse {
    String month;
    Long countMonth;
    Boolean mostRecordMonth = false;

    public void updateMostRecordMonth(){
        this.mostRecordMonth = true;
    }
}
