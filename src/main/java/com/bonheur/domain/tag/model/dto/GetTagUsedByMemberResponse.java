package com.bonheur.domain.tag.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTagUsedByMemberResponse {
    private Long tagId;
    private String tagName;

    public static GetTagUsedByMemberResponse of(Long tagId,String tagName) {
        return new GetTagUsedByMemberResponse(tagId, tagName);
    }
}