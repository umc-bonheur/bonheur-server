package com.bonheur.domain.tag.controller;

import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.tag.model.dto.CreateTagRequest;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
import com.bonheur.domain.tag.model.dto.GetTagIdResponse;
import com.bonheur.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class TagController {
    private final TagService tagService;

    @ApiDocumentResponse
    @Operation(summary = "해시태그 생성")
    @PostMapping("/api/tags")
    public ApiResponse<CreateTagResponse> createTags(
            @Valid @RequestBody CreateTagRequest createTagRequest) {
        Long memberId = 1L; //session 관련 검증 추가해야 함!
        return ApiResponse.success(tagService.createTags(memberId, createTagRequest.getTags()));
    }

    // # tagName으로 tagId 조회
    // 회원 인증 어노테이션 추가 필요
    @ApiDocumentResponse
    @Operation(summary = "tagName으로 tagId 조회")
    @GetMapping("/api/tag/{tagName}")
    public ApiResponse<GetTagIdResponse> getTagIdByTagName(@PathVariable(value = "tagName") @RequestParam String tagName, Long memberId) {
        GetTagIdResponse getTagIdResponse = tagService.getTagIdByTagName(memberId, tagName);
        return ApiResponse.success(getTagIdResponse);
    }
}
