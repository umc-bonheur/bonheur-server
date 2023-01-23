package com.bonheur.domain.member.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindByMonthResponse {
    String month;
    Long countMonth;
}
