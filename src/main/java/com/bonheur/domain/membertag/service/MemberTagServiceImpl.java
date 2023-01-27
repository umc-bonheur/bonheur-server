package com.bonheur.domain.membertag.service;

import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.membertag.model.MemberTag;
import com.bonheur.domain.membertag.repository.MemberTagRepository;
import com.bonheur.domain.tag.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService {
    private final MemberTagRepository memberTagRepository;

    @Override
    @Transactional
    public void createMemberTags(Member member, List<Tag> tags){    //태그와 사용자 맵핑(연결)
        tags.forEach(tag -> {
                Optional<MemberTag> memberTag = memberTagRepository.findMemberTagByTag(tag);
                if(memberTag.isEmpty()){
                    memberTagRepository.save(MemberTag.newMemberTag(member, tag));
                }
                else{
                    memberTag.get().updateUpdatedAt(LocalDateTime.now());
                }
            }
        );
    }
}
