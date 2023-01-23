package com.bonheur.domain.membertag.service;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.membertag.model.MemberTag;
import com.bonheur.domain.membertag.repository.MemberTagRepository;
import com.bonheur.domain.tag.model.Tag;
import com.bonheur.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService {
    private final MemberTagRepository memberTagRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public void createMemberTags(Member member, List<Long> tagsIds){    //태그와 사용자 맵핑(연결)
        for(Long tagId : tagsIds) {
            Tag tag = tagRepository.findTagById(tagId);
            Optional<MemberTag> memberTag = memberTagRepository.findById(tagId);
            if(memberTag.isEmpty()){
                memberTagRepository.save(MemberTag.newMemberTag(member, tag));
            }
            else{
                memberTag.get().updateUpdatedAt(LocalDateTime.now());
            }
        }
    }
}
