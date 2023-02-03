package com.bonheur.domain.tag.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTagIdResponse {
    private Long tagId;

    public static GetTagIdResponse of(@NotNull Long tagId) {
        return new GetTagIdResponse(tagId);
    }
}
