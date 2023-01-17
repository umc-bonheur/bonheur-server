package com.bonheur.domain.auth.model.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String token;

    private Long userId;

    public static LoginResponse of(String sessionId, Long userId) {
        return new LoginResponse(sessionId, userId);
    }
}
