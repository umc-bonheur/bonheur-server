package com.bonheur.domain.auth.model.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String sessionId;

    private Long memberId;

    public static LoginResponse of(String sessionId, Long memberId) {
        return new LoginResponse(sessionId, memberId);
    }
}
