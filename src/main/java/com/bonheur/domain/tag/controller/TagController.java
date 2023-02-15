package com.bonheur.domain.tag.controller;

import com.bonheur.config.interceptor.Auth;
import com.bonheur.config.resolver.MemberId;
import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.tag.model.dto.GetTagUsedByMemberResponse;
import com.bonheur.domain.tag.model.dto.CreateTagRequest;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
import com.bonheur.domain.tag.model.dto.GetTagIdResponse;
import com.bonheur.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController {
    private final TagService tagService;

    @ApiDocumentResponse
    @Operation(summary = "해시태그 생성")
    @PostMapping("/api/tags")
    @Auth
    public ApiResponse<CreateTagResponse> createTags(
            @Valid @MemberId Long memberId,
            @Valid @RequestBody CreateTagRequest createTagRequest) {
        return ApiResponse.success(tagService.createTags(memberId, createTagRequest.getTags()));
    }

    // # tagName으로 tagId 조회
    @ApiDocumentResponse
    @Operation(summary = "tagName으로 tagId 조회")
    @GetMapping("/api/tags/{tagName}")
    @Auth
    public ApiResponse<GetTagIdResponse> getTagIdByTagName(@PathVariable(value = "tagName") String tagName,
                                                           @Valid @MemberId Long memberId) {
        GetTagIdResponse getTagIdResponse = tagService.getTagIdByTagName(memberId, tagName);
        return ApiResponse.success(getTagIdResponse);
    }

    @ApiDocumentResponse
    @Operation(summary = "회원 최근 사용 태그 조회")
    @GetMapping("/api/tags")
    @Auth
    public ApiResponse<List<GetTagUsedByMemberResponse>> getTagUsedByMember(@Valid @MemberId Long memberId) {
        return ApiResponse.success(tagService.getTagUsedByMember(memberId));
    }
}
