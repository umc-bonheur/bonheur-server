package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor
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

    @Builder(access = AccessLevel.PRIVATE)
    private FindByTimeResponse(Long morning, Long afternoon, Long evening, Long night, Long dawn){
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
        this.night = night;
        this.dawn = dawn;
    }

    public static FindByTimeResponse of(Long morning, Long afternoon, Long evening, Long night, Long dawn){
        return FindByTimeResponse.builder()
                .morning(morning)
                .afternoon(afternoon)
                .evening(evening)
                .night(night)
                .dawn(dawn)
                .build();
    }
}
