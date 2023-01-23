package com.bonheur.domain.member.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.membertag.model.MemberTag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Embedded
    private MemberSocialInfo memberSocialInfo;

    @Column(length = 30)
    private String nickname;

    @Embedded
    private MemberProfile profile;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTag> memberTags = new ArrayList<>();


    @Builder(access = AccessLevel.PRIVATE)
    private Member(String socialId, MemberSocialType socialType, String nickname) {
        this.memberSocialInfo = MemberSocialInfo.of(socialId, socialType);
        this.nickname = nickname;
    }


    public static Member newMember(String socialId, MemberSocialType socialType, String nickname) {
        return Member.builder()
                .socialId(socialId)
                .socialType(socialType)
                .nickname(nickname)
                .build();
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateProfile(String url, String path){
        this.profile = MemberProfile.of(url, path);
    }
}
