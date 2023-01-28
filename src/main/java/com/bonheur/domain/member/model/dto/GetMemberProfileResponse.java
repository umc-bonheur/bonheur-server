package com.bonheur.domain.member.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMemberProfileResponse {
    private String nickname;
    private String image;

    public static GetMemberProfileResponse of(String nickname, String image){
        return new GetMemberProfileResponse(nickname, image);
    }
}
