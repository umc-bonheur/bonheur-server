package com.bonheur.config.client.apple;

import com.bonheur.config.client.apple.dto.AppleTokenPayload;
import com.bonheur.domain.common.exception.InvalidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;

import static com.bonheur.domain.common.exception.dto.ErrorCode.E400_INVALID_AUTH_TOKEN;

@RequiredArgsConstructor
@Component
public class AppleTokenDecoder {

    @Value("${apple.client_id}")
    private String clientId;

    @Value("${apple.issuer}")
    private String issuer;
    private final ObjectMapper objectMapper;

    public String getSocialIdFromToken(String token) {
        try {
            String payload = token.split("\\.")[1];
            String decodedPayload = new String(Base64.getDecoder().decode(payload));
            AppleTokenPayload appleTokenPayload = objectMapper.readValue(decodedPayload, AppleTokenPayload.class);
            validateToken(appleTokenPayload);
            return appleTokenPayload.getSub();
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidException(String.format("잘못된 토큰 (%s) 입니다", token), E400_INVALID_AUTH_TOKEN);
        }
    }

    private void validateToken(@NotNull AppleTokenPayload payload) {
        if (!payload.getIss().equals(issuer))
            throw new InvalidException(String.format("잘못된 애플 토큰 입니다 - issuer가 일치하지 않습니다 payload: (%s)", payload), E400_INVALID_AUTH_TOKEN);
        if (!payload.getAud().equals(clientId))
            throw new InvalidException(String.format("잘못된 애플 토큰 입니다 - clientId가 일치하지 않습니다 payload: (%s)", payload), E400_INVALID_AUTH_TOKEN);

        // 토큰 만료 시간 추가 로직 필요 ( 애플 토큰은 시간이 무제한 )
    }
}
