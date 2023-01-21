package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardByTagRequest {
    private Long memberId;
    private List<Long> tagIds;
}
