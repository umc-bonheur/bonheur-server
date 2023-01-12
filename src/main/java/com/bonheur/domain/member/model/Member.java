package com.bonheur.domain.member.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
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
}
