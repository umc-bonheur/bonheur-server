package com.bonheur.domain.tag.service;

import com.bonheur.domain.tag.model.dto.CreateTagResponse;

import java.util.List;

public interface TagService {
    CreateTagResponse createTags(List<String> tags);
}
