package com.bonheur.domain.tag.service;

import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public CreateTagResponse createTags(List<String> tags){
        List<Long> tagsIds = new ArrayList<>();
        for(String tag : tags) {
            Optional<Tag> oldTag = tagRepository.findTagByName(tag);    //기존 tag 테이블에 동일한 태그가 있는 경우
            if (oldTag.isEmpty()){  //기존 tag 테이블에 동일한 태그가 없는 경우
                Tag tag1 = Tag.newTag(tag);
                tagsIds.add(tagRepository.save(tag1).getId());
            }
            else{
                tagsIds.add(oldTag.get().getId());
            }
        }
        return CreateTagResponse.of(tagsIds);
    }
}
