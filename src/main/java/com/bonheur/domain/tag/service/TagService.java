package com.bonheur.domain.tag.service;

import com.bonheur.domain.tag.model.dto.GetTagUsedByMemberResponse;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
import com.bonheur.domain.tag.model.dto.GetTagIdResponse;

import java.util.List;

public interface
TagService {
    CreateTagResponse createTags(Long memberId, List<String> tags);

    GetTagIdResponse getTagIdByTagName(Long memberId, String tagName);
    List<GetTagUsedByMemberResponse> getTagUsedByMember(Long memberId);
}
