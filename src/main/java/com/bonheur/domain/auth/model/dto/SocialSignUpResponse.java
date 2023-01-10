package com.bonheur.domain.auth.model.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialSignUpResponse {

    private String token;

    private Long memberId;

    public static SocialSignUpResponse of(String sessionId, Long memberId) {
        return new SocialSignUpResponse(sessionId, memberId);
    }

}