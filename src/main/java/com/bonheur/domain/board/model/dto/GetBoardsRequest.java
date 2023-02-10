package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardsRequest {
    @Nullable
    private String orderType;

    @Nullable
    private Long lastBoardId;

    public void update(String orderType) {
        this.orderType = orderType;
    }
}
