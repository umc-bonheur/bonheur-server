package com.bonheur.domain.membertag.model;

import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.tag.model.Tag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class MemberTag extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder(access = AccessLevel.PRIVATE)
    public MemberTag(Member member, Tag tag) {
        this.member = member;
        this.tag = tag;
    }

    public static MemberTag newMemberTag(Member member, Tag tag) {
        return MemberTag.builder()
                .member(member)
                .tag(tag)
                .build();
    }
}
