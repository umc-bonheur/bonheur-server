package com.bonheur.domain.member.model;

import com.bonheur.domain.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Embedded
    private MemberSocialInfo memberSocialInfo;

    @Column(length = 30)
    private String nickname;

    private String profileImage;
}
