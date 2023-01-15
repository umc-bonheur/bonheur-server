package com.bonheur.domain.tag.service;

import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public List<Tag> createTags(List<String> tags){
        List<Tag> tagList = null;
        for(String tag : tags) {
            Optional<Tag> oldTag = tagRepository.findTagByName(tag);
            if (oldTag.isEmpty()){
                Tag tag1 = Tag.newTag(tag);
                tagRepository.save(tag1);
                tagList.add(tag1);
            }
            else{
                tagList.add(oldTag.get());
            }
        }
        return tagList;
    }
}
