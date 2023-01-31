package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindDayRecordResponse {
    private String dayOfWeek;
    private Long countDay;
    private Boolean mostRecordDay = false;

    private FindDayRecordResponse(String dayOfWeek, Long countDay) {
        this.dayOfWeek = dayOfWeek;
        this.countDay = countDay;
    }

    public static FindDayRecordResponse createFindDayRecordResponse(String dayOfWeek, Long countDay){
        return new FindDayRecordResponse(dayOfWeek, countDay);
    }

    public void updateMostRecordDay(){
        this.mostRecordDay = true;
    }
    public void updateCountDay(Long countDay){
        this.countDay = countDay;
    }
}
