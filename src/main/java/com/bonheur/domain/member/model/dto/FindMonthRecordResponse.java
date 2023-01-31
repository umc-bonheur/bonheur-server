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

    private FindMonthRecordResponse(String month, Long countMonth) {
        this.month = month;
        this.countMonth = countMonth;
    }

    public static FindMonthRecordResponse createFindMonthRecordResponse(String month, Long countMonth){
        return new FindMonthRecordResponse(month,countMonth);
    }

    public void updateMostRecordMonth(){
        this.mostRecordMonth = true;
    }

    public void updateCountMonth(Long countMonth){
        this.countMonth = countMonth;
    }
}
