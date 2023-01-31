package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindTimeRecordResponse {
    private String time;
    private Long countTime;
    private Boolean mostRecordTime = false;

    private FindTimeRecordResponse(String time, Long countTime) {
        this.time = time;
        this.countTime = countTime;
    }

    public static FindTimeRecordResponse createFindTimeRecordResponse(String time, Long countTime){
        return new FindTimeRecordResponse(time, countTime);

    }
    public void updateMostRecordTime(){
        this.mostRecordTime = true;
    }

}
