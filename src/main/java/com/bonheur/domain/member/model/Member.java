package com.bonheur.domain.member.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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


    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();


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
}
