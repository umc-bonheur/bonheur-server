package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberProfileRequest {
    private String nickname;

    @Builder(access = AccessLevel.PRIVATE)
    private UpdateMemberProfileRequest(String nickname) {
        this.nickname = nickname;
    }

    public static UpdateMemberProfileRequest of(String nickname){
        return UpdateMemberProfileRequest.builder()
                .nickname(nickname)
                .build();
    }
}
