package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindByDayResponse {
    private String dayOfWeek;
    private Long countDay;
}
