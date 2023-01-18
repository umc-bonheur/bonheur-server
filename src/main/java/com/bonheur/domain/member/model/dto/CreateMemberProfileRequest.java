package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemberProfileRequest {
    private String nickname;

    @Builder(access = AccessLevel.PRIVATE)
    private CreateMemberProfileRequest(String nickname) {
        this.nickname = nickname;
    }

    public static CreateMemberProfileRequest of(String nickname){
        return CreateMemberProfileRequest.builder()
                .nickname(nickname)
                .build();
    }
}
