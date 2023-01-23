package com.bonheur.domain.tag.service;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.membertag.service.MemberTagService;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.model.dto.CreateTagResponse;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final MemberTagService memberTagService;
    @Override
    @Transactional
    public CreateTagResponse createTags(Long memberId, List<String> tags){
        Member member = memberRepository.findMemberById(memberId);
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
        memberTagService.createMemberTags(member, tagsIds);

        return CreateTagResponse.of(tagsIds);
    }
}