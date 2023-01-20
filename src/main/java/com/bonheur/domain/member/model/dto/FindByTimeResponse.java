package com.bonheur.domain.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindByTimeResponse {
    // 06-12
    private Long morning;

    // 12-18
    private Long afternoon;

    // 18-20
    private Long evening;

    //20-01
    private Long night;

    //01-06
    private Long dawn;
    private Long mostRecordTime;
}
