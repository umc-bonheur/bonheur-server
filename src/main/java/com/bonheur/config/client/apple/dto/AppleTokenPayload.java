package com.bonheur.config.client.apple.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppleTokenPayload {

    private String iss;

    private String aud;

    private Long exp;

    private Long iat;

    private String sub;

    private String cHash;

    private Long authTime;

    private Boolean nonceSupported;

    private Boolean emailVerified;

    private String email;

    private AppleTokenPayload(String sub, String email) {
        this.sub = sub;
        this.email = email;
    }

    public static AppleTokenPayload testInstance(String uid, String email) {
        return new AppleTokenPayload(uid, email);
    }
}