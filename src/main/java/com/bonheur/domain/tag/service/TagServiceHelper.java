package com.bonheur.domain.tag.service;

import com.bonheur.domain.common.exception.NotFoundException;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.bonheur.domain.common.exception.dto.ErrorCode.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagServiceHelper {

    public static Tag getExistOwnTagByTagId(TagRepository tagRepository, Long memberId, Long tagId){
        Tag tag = tagRepository.findOwnTagByTagId(memberId, tagId);
        if(tag == null){
            throw new NotFoundException("존재하지 않는 태그입니다", E404_NOT_EXISTS_Tag);
        }
        return tag;
    }
}
