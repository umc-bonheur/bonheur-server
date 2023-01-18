package com.bonheur.domain.member.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile {
    private String url;
    private String path;

    private MemberProfile(String url, String path) {
        this.url = url;
        this.path = path;
    }

    public static MemberProfile of(String url, String path) {
        return new MemberProfile(url, path);
    }
    public void updateProfile(String url, String path){
        this.url = url;
        this.path = path;
    }
}
