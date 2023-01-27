package com.bonheur.domain.tag.controller;

import com.bonheur.config.swagger.dto.ApiDocumentResponse;
import com.bonheur.domain.common.dto.ApiResponse;
import com.bonheur.domain.tag.model.dto.CreateTagRequest;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
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
}
