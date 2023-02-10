package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsGroupsResponse {
    private Map<String, List<GetBoardsResponse>> group;
    private String orderType; // newest : 최신, oldest : 오래된 순
    private Boolean last; // page의 마지막 여부

    public static GetBoardsGroupsResponse of(Map<String, List<GetBoardsResponse>> group, String orderType, Boolean last) {
        group = group.entrySet().stream().sorted(
                orderType.equals("newest") ? Map.Entry.<String, List<GetBoardsResponse>>comparingByKey().reversed()
                        : Map.Entry.comparingByKey()).collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        return new GetBoardsGroupsResponse(group, orderType, last);
    }
}
