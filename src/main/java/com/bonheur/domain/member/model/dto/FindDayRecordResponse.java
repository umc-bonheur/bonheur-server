package com.bonheur.domain.member.model.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindDayRecordResponse {
    private String dayOfWeek;
    private Long countDay;
    private Boolean mostRecordDay = false;

    public FindDayRecordResponse updateMostRecordDay(){
        this.mostRecordDay = true;
        return this;
    }
}
