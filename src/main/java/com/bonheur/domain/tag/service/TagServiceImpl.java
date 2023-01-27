package com.bonheur.domain.tag.service;

import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.membertag.service.MemberTagService;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final MemberTagService memberTagService;

    @Override
    @Transactional
    public CreateTagResponse createTags(Long memberId, List<String> tags){
        List<Tag> tagList = new ArrayList<>();
        tags.forEach( tag -> {
                Tag hashtag = tagRepository.findTagByName(tag).orElseGet(() -> tagRepository.save(Tag.newTag(tag)));
                tagList.add(hashtag);
            }
        );
        memberTagService.createMemberTags(memberRepository.findMemberById(memberId), tagList);

        return CreateTagResponse.of(tagList.stream().map(BaseEntity::getId).collect(Collectors.toList()));
    }
}