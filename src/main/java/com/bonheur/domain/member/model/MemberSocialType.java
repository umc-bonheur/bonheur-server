package com.bonheur.domain.member.model;

import com.bonheur.domain.common.EnumModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberSocialType implements EnumModel {

    APPLE("애플"),
    KAKAO("카카오"),
    ;

    private final String description;

    @NotNull
    @Override
    public String getKey() {
        return name();
    }

    @JsonCreator
    public static MemberSocialType from(String text) {
        for(MemberSocialType type : MemberSocialType.values()){
            if(type.getDescription().equals(text))
                return type;
        }
        throw new IllegalArgumentException();
    }
}
