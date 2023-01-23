package com.bonheur.domain.member.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindByMonthResponse {
    Long jan;
    Long feb;
    Long mar;
    Long apr;
    Long may;
    Long jun;
    Long jul;
    Long aug;
    Long sept;
    Long oct;
    Long nov;
    Long dec;

    @Builder(access = AccessLevel.PRIVATE)
    private FindByMonthResponse(Long jan, Long feb, Long mar, Long apr, Long may, Long jun, Long jul, Long aug, Long sept, Long oct, Long nov, Long dec){
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sept = sept;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
    }

    public static FindByMonthResponse of(Long jan, Long feb, Long mar, Long apr, Long may, Long jun, Long jul, Long aug, Long sept, Long oct, Long nov, Long dec){
        return FindByMonthResponse.builder()
                .jan(jan)
                .feb(feb)
                .mar(mar)
                .apr(apr)
                .may(may)
                .jun(jun)
                .jul(jul)
                .aug(aug)
                .sept(sept)
                .oct(oct)
                .nov(nov)
                .dec(dec)
                .build();
    }
}
