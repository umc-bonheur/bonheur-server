package com.bonheur.domain.member.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindByDayResponse {
    private Long sun;
    private Long mon;
    private Long tue;
    private Long wed;
    private Long thu;
    private Long fri;
    private Long sat;


    @Builder(access = AccessLevel.PRIVATE)
    private FindByDayResponse(Long sun, Long mon, Long tue, Long wed, Long thu, Long fri, Long sat){
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }

    public static FindByDayResponse of(Long sun, Long mon, Long tue, Long wed, Long thu, Long fri, Long sat){
        return FindByDayResponse.builder()
                .sun(sun)
                .mon(mon)
                .tue(tue)
                .wed(wed)
                .thu(thu)
                .fri(fri)
                .sat(sat)
                .build();
    }
}
