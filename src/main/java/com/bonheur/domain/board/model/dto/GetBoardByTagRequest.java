package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardByTagRequest {
    @Nullable
    private String orderType;

    @Nullable
    private Long lastBoardId;

    @NotEmpty(message = "태그 아이디를 입력해주세요.")
    private List<Long> tagIds;
}
