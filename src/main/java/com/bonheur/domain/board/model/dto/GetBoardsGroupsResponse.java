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

    public static GetBoardsGroupsResponse of(Map<String, List<GetBoardsResponse>> group, String orderType) {
        if (orderType.equals("newest")) {
            group = group.entrySet().stream().sorted(Map.Entry.<String, List<GetBoardsResponse>>comparingByKey().reversed()).collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
        }
        else {
            group = group.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                    Map.Entry::getKey, Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));
        }
        return new GetBoardsGroupsResponse(group, orderType);
    }
}
